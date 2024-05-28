package no.nibio.ipmdisipproxy.api;

import no.nibio.ipmdisipproxy.exception.BadRequestException;
import no.nibio.ipmdisipproxy.model.WeatherDataParameter;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestConverterTest {

    public static final List<Integer> PARAMETERS = Arrays.asList(1001, 2001, 3001);
    public static final List<List<Double>> VALUES = Arrays.asList(Arrays.asList(10.1, 22.1, 31.3), Arrays.asList(3.1, 25.3, 38.9));

    @Test
    public void testConvertDateTime() {
        ZonedDateTime start = ZonedDateTime.parse("2024-02-14T23:00:00Z");
        ZonedDateTime end = ZonedDateTime.parse("2024-02-15T05:00:00Z");
        int interval = 3600;

        List<String> dateTimeList = RequestConverter.createDateTimeList(start, end, interval);
        assertThat(dateTimeList, notNullValue());
        assertThat(dateTimeList.size(), equalTo(7));
        assertThat(dateTimeList.get(0), equalTo("2024-02-14 23:00"));
        assertThat(dateTimeList.get(6), equalTo("2024-02-15 05:00"));
    }

    @Test
    public void testConvertTemperature() {
        List<Double> tempValues = RequestConverter.convertWeatherDataParameterValues(PARAMETERS, VALUES, WeatherDataParameter.TEMPERATURE_INSTANT, WeatherDataParameter.TEMPERATURE_MEAN);
        assertThat(tempValues, notNullValue());
        assertThat(tempValues.size(), equalTo(2));
        assertThat(tempValues.get(0), equalTo(10.1));
        assertThat(tempValues.get(1), equalTo(3.1));
    }

    @Test
    public void testConvertPrecipitation() {
        List<Double> precValues = RequestConverter.convertWeatherDataParameterValues(PARAMETERS, VALUES, WeatherDataParameter.PRECIPITATION);
        assertThat(precValues, notNullValue());
        assertThat(precValues.size(), equalTo(2));
        assertThat(precValues.get(0), equalTo(22.1));
        assertThat(precValues.get(1), equalTo(25.3));
    }

    @Test
    public void testConvertHumidity() {
        List<Double> totPrecValues = RequestConverter.convertWeatherDataParameterValues(PARAMETERS, VALUES, WeatherDataParameter.HUMIDITY_INSTANT, WeatherDataParameter.HUMIDITY_MEAN);
        assertThat(totPrecValues, notNullValue());
        assertThat(totPrecValues.size(), equalTo(2));
        assertThat(totPrecValues.get(0), equalTo(31.3));
        assertThat(totPrecValues.get(1), equalTo(38.9));
    }

    @Test
    public void testConvertWithMissingData() {
        List<Integer> parameters = Collections.singletonList(1001);
        List<List<Double>> values = Arrays.asList(Collections.singletonList(10.1), Collections.singletonList(3.1));
        assertThrows(BadRequestException.class, () -> RequestConverter.convertWeatherDataParameterValues(parameters, values, WeatherDataParameter.HUMIDITY_MEAN));
    }


}