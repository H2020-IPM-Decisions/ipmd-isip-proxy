package no.nibio.ipmdisipproxy.api;

import no.nibio.ipmdisipproxy.exception.ExternalApiException;
import no.nibio.ipmdisipproxy.model.IsipRequest;
import no.nibio.ipmdisipproxy.model.IsipResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class IsipService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IsipService.class);
    private static final String PARAM_TOKEN = "pld";
    private final RestTemplate restTemplate;

    @Value("${model.base.url}")
    private String baseUrl;

    public IsipService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public IsipResponse triggerSiggetreide(IsipRequest isipRequest, String token) {
        String url = buildUrl(token);
        ResponseEntity<IsipResponse> response;
        try {
            LOGGER.info("POST request to {}", baseUrl);
            response = restTemplate.postForEntity(url, isipRequest, IsipResponse.class);
        } catch (HttpClientErrorException e) {
            LOGGER.error("Client error: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            throw new ExternalApiException("Client error occurred: " + e.getMessage(), e);
        } catch (HttpServerErrorException e) {
            LOGGER.error("Server error: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            throw new ExternalApiException("Server error occurred: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error: " + e.getMessage());
            throw new ExternalApiException("Unexpected error occurred: " + e.getMessage(), e);
        }
        if (response.getStatusCode().is3xxRedirection()) {
            LOGGER.error("{} from ISIP, possibly means that input data is not valid", response.getStatusCode());
            throw new ExternalApiException("No response returned from the ISIP server. Please check your input.");
        }
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new ExternalApiException("Failed to get a successful response from the server. Status code: " + response.getStatusCode());
    }

    private String buildUrl(String token) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam(PARAM_TOKEN, token)
                .toUriString();
    }
}
