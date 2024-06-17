package no.nibio.ipmdisipproxy.service;

import no.nibio.ipmdisipproxy.exception.ExternalApiException;
import no.nibio.ipmdisipproxy.model.IsipRequest;
import no.nibio.ipmdisipproxy.model.IsipResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @since 1.0.0
 */
@SpringBootTest
class IsipServiceTest {

    public static final String TOKEN = "thisisatoken";
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private IsipService isipService;

    @Value("${model.base.url}")
    private String baseUrl;

    @Test
    void testPropertyIsLoaded() {
        assertThat(baseUrl, equalTo("https://www.isip.de/charts/api/models/siggetreide"));
    }

    @Test
    public void testTriggerSiggetreideClientError() {
        HttpClientErrorException ex = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "");
        when(restTemplate.postForEntity(any(String.class), any(), any(Class.class))).thenThrow(ex);

        ExternalApiException exception = assertThrows(ExternalApiException.class, () -> {
            isipService.triggerSiggetreide(new IsipRequest(), TOKEN);
        });
        assertThat(exception.getMessage(), equalTo("Client error occurred: 400 Bad Request"));
    }

    @Test
    public void testTriggerSiggetreideServerError() {
        HttpServerErrorException ex = new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE, "");
        when(restTemplate.postForEntity(any(String.class), any(), any(Class.class))).thenThrow(ex);

        ExternalApiException exception = assertThrows(ExternalApiException.class, () -> {
            isipService.triggerSiggetreide(new IsipRequest(), TOKEN);
        });
        assertThat(exception.getMessage(), equalTo("Server error occurred: 503 Service Unavailable"));
    }

    @Test
    public void testTriggerSiggetreideValidResponse() {
        ResponseEntity<IsipResponse> response = new ResponseEntity<>(new IsipResponse(), HttpStatus.OK);
        when(restTemplate.postForEntity(any(String.class), any(), any(Class.class))).thenReturn(response);

        IsipResponse result = isipService.triggerSiggetreide(new IsipRequest(), TOKEN);
        assertThat(result, notNullValue());
    }

}