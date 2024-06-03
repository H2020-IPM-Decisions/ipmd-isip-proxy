package no.nibio.ipmdisipproxy.api;

import no.nibio.ipmdisipproxy.TestUtils;
import no.nibio.ipmdisipproxy.model.Disease;
import no.nibio.ipmdisipproxy.model.IpmdResponse;
import no.nibio.ipmdisipproxy.model.IsipResponse;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;

class ResponseConverterTest {

    public static final String EUROPE_OSLO = "Europe/Oslo";
    private static final Double LONGITUDE = 10.520647689700128;
    private static final Double LATITUDE = 52.94340701794777;
    private static final Disease DISEASE = Disease.BROWN_RUST;
    private static final List<String> DATES = Arrays.asList("2024-02-01", "2024-02-02", "2024-02-03");

    @Test
    public void testConversion() {
        IsipResponse isipResponse = TestUtils.readJsonFromFile("src/test/resources/isip_response.json", IsipResponse.class);
        IpmdResponse ipmdResponse = ResponseConverter.isipToImpdResponse(EUROPE_OSLO, LONGITUDE, LATITUDE, DISEASE, isipResponse);
        assertThat(ipmdResponse, notNullValue());
        assertThat(ipmdResponse.getLocationResult().get(0).getLongitude(), equalTo(LONGITUDE));
        assertThat(ipmdResponse.getLocationResult().get(0).getLatitude(), equalTo(LATITUDE));

        List<String> isipWarningStatuses = isipResponse.getNodes().getResult().getVariables().get("brown rust").getData();
        List<Integer> ipmdWarningStatuses = ipmdResponse.getLocationResult().get(0).getWarningStatus();
        int currentIndex = 0;
        for (String isipWS : isipWarningStatuses) {
            switch (isipWS) {
                case "0":
                    assertThat(ipmdWarningStatuses.get(currentIndex), equalTo(0));
                    break;
                case "1":
                    assertThat(ipmdWarningStatuses.get(currentIndex), equalTo(2));
                    break;
                case "2":
                    assertThat(ipmdWarningStatuses.get(currentIndex), equalTo(3));
                    break;
                case "3":
                    assertThat(ipmdWarningStatuses.get(currentIndex), equalTo(4));
                    break;
                default:
                    fail("Unexpected ISIP warning status " + isipWS);
            }
            currentIndex++;
        }
    }

    @Test
    public void findDateTimeStartValid() {
        String dateTimeStart = ResponseConverter.findDateTimeStart(EUROPE_OSLO, DATES);
        assertThat(dateTimeStart, notNullValue());
        assertThat(dateTimeStart, CoreMatchers.equalTo("2024-02-01T00:00:00Z"));
    }

    @Test
    public void findDateTimeStartEmpty() {
        String dateTimeStart = ResponseConverter.findDateTimeStart(EUROPE_OSLO, Collections.emptyList());
        assertThat(dateTimeStart, nullValue());
    }

    @Test
    public void findDateTimeEndValid() {
        String dateTimeEnd = ResponseConverter.findDateTimeEnd(EUROPE_OSLO, DATES);
        assertThat(dateTimeEnd, notNullValue());
        assertThat(dateTimeEnd, CoreMatchers.equalTo("2024-02-03T23:00:00Z"));
    }

    @Test
    public void findDateTimeEndEmpty() {
        String dateTimeEnd = ResponseConverter.findDateTimeEnd(EUROPE_OSLO, Collections.emptyList());
        assertThat(dateTimeEnd, nullValue());
    }

}