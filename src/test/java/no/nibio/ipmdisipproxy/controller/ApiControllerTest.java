package no.nibio.ipmdisipproxy.controller;

import no.nibio.ipmdisipproxy.TestUtils;
import no.nibio.ipmdisipproxy.model.IsipRequest;
import no.nibio.ipmdisipproxy.model.IsipResponse;
import no.nibio.ipmdisipproxy.service.GISService;
import no.nibio.ipmdisipproxy.service.IsipService;
import no.nibio.ipmdisipproxy.service.TimeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ApiController.class)
public class ApiControllerTest {
    private static final String SIMULATION_DATE = "2024-05-01";
    private static final String TOKEN = "thisisatoken";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IsipService isipService;

    @MockBean
    private TimeService timeService;

    @MockBean
    private GISService gisService;

    @InjectMocks
    private ApiController apiController;

    @BeforeEach
    public void setup() {
        when(timeService.getCurrentDate()).thenReturn(LocalDate.parse(SIMULATION_DATE));
        when(isipService.triggerSiggetreide(any(IsipRequest.class), eq(TOKEN)))
                .thenReturn(
                        TestUtils.readJsonFromFile(
                                "src/test/resources/isip_response.json", IsipResponse.class));
    }

    @Test
    public void testValidPostCallInGermany() throws Exception {
        String impdRequest = TestUtils.readStringFromFile("src/test/resources/ipmd_request.json");
        when(gisService.isLocationInGermany(anyDouble(), anyDouble())).thenReturn(true);

        mockMvc.perform(post("/siggetreide")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(impdRequest)
                        .header("Authorization", "Bearer " + TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeStart").value("2024-02-15T00:00:00Z"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeEnd").value("2024-05-22T23:00:00Z"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.interval").value(86400));

        ArgumentCaptor<IsipRequest> isipRequestCaptor = ArgumentCaptor.forClass(IsipRequest.class);
        verify(isipService).triggerSiggetreide(isipRequestCaptor.capture(), eq(TOKEN));
        IsipRequest capturedIsipRequest = isipRequestCaptor.getValue();
        assertThat(capturedIsipRequest.getVariables().getDateTime(), Matchers.nullValue());
    }

    @Test
    public void testValidPostCallNotInGermany() throws Exception {
        String impdRequest = TestUtils.readStringFromFile("src/test/resources/ipmd_request.json");
        when(gisService.isLocationInGermany(anyDouble(), anyDouble())).thenReturn(false);

        mockMvc.perform(post("/siggetreide")
                .contentType(MediaType.APPLICATION_JSON)
                .content(impdRequest)
                .header("Authorization", "Bearer " + TOKEN));

        ArgumentCaptor<IsipRequest> isipRequestCaptor = ArgumentCaptor.forClass(IsipRequest.class);
        verify(isipService).triggerSiggetreide(isipRequestCaptor.capture(), eq(TOKEN));
        IsipRequest capturedIsipRequest = isipRequestCaptor.getValue();
        assertThat(capturedIsipRequest.getVariables().getDateTime(), Matchers.notNullValue());
    }

    @Test
    public void testPostCallWithoutAuthorizationHeader() throws Exception {
        String impdRequest = TestUtils.readStringFromFile("src/test/resources/ipmd_request.json");

        mockMvc
                .perform(post("/siggetreide").contentType(MediaType.APPLICATION_JSON).content(impdRequest))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void testPostCallWithInvalidAuthorizationHeader() throws Exception {
        String impdRequest = TestUtils.readStringFromFile("src/test/resources/ipmd_request.json");

        mockMvc
                .perform(
                        post("/siggetreide")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(impdRequest)
                                .header("Authorization", "BrownBear " + TOKEN))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
