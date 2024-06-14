package no.nibio.ipmdisipproxy.service;

import no.nibio.ipmdisipproxy.exception.BadRequestException;
import no.nibio.ipmdisipproxy.model.*;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static no.nibio.ipmdisipproxy.model.WeatherDataParameter.*;

/**
 * Factory class for creating {@link IsipRequest} objects.
 *
 * <p>This class provides static methods for creating instances of {@link IsipRequest}. It abstracts
 * the creation logic, making it easier to manage the instantiation process.
 *
 * @see IsipRequest
 * @since 0.0.1
 */
public class IsipRequestFactory {

    /**
     * Create basic ISIP request from the given information
     *
     * @param crop           The crop to run model for
     * @param latitude       The latitude
     * @param longitude      The longitude
     * @param simulationDate The simulation date, YYYY-MM-DD
     * @return The ISIP request
     */
    public static IsipRequest createIsipRequest(
            Crop crop, Disease disease, Double latitude, Double longitude, String simulationDate) {
        return new IsipRequest(
                latitude, longitude, simulationDate, crop.getIsipName(), disease.getIsipName());
    }

    public static void appendWeatherData(IsipRequest isipRequest, IpmdWeatherData weatherData) {
        IpmdLocationWeatherData locationWeatherData = weatherData.getLocationWeatherData().get(0);
        List<Integer> weatherParameters = weatherData.getWeatherParameters();
        List<List<Double>> weatherDataValues = locationWeatherData.getData();

        IsipRequest.Variables variables = isipRequest.getVariables();
        variables.setDateTime(
                createDateTimeList(
                        weatherData.getTimeStart(), weatherData.getTimeEnd(), weatherData.getInterval()));
        variables.setT2m(
                convertWeatherDataParameterValues(
                        weatherParameters, weatherDataValues, TEMPERATURE_INSTANT, TEMPERATURE_MEAN));
        variables.setTotPrec(
                convertWeatherDataParameterValues(weatherParameters, weatherDataValues, PRECIPITATION));
        variables.setRelhum2m(
                convertWeatherDataParameterValues(
                        weatherParameters, weatherDataValues, HUMIDITY_INSTANT, HUMIDITY_MEAN));
    }

    /**
     * Create list of datetime strings from the given start time to the given end time, with given
     * interval
     *
     * @param timeStart Start of period
     * @param timeEnd   End of period
     * @param interval  Interval in seconds
     * @return List of datetime strings in format 'yyyy-MM-dd HH:mm'
     */
    public static List<String> createDateTimeList(
            ZonedDateTime timeStart, ZonedDateTime timeEnd, Integer interval) {
        DateTimeFormatter isipDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        List<String> dateTimeList = new ArrayList<>();
        ZonedDateTime current = timeStart;
        Duration duration = Duration.ofSeconds(interval);
        while (!current.isAfter(timeEnd)) {
            dateTimeList.add(isipDateTimeFormatter.format(current));
            current = current.plus(duration);
        }
        return dateTimeList;
    }

    /**
     * Convert weather data parameter values to ISIP format
     *
     * @param weatherParameters   Gives the order of the weather parameters in the original request
     * @param locationWeatherData List of lists of weather data parameter values
     * @param parameters          Which parameters should be included
     * @return A list of values for the given parameters
     */
    public static List<Double> convertWeatherDataParameterValues(
            List<Integer> weatherParameters,
            List<List<Double>> locationWeatherData,
            WeatherDataParameter... parameters) {
        int indexOfParameter = -1;
        for (WeatherDataParameter parameter : parameters) {
            if (weatherParameters.contains(parameter.getIpmCode())) {
                indexOfParameter = weatherParameters.indexOf(parameter.getIpmCode());
                break;
            }
        }
        if (indexOfParameter < 0) {
            String ipmCodesString =
                    Stream.of(parameters)
                            .map(WeatherDataParameter::getIpmCode)
                            .map(String::valueOf)
                            .collect(Collectors.joining("/"));
            throw new BadRequestException(
                    String.format(
                            "Required weather data parameter [%s] missing from request", ipmCodesString));
        }

        List<Double> valueList = new ArrayList<>();
        for (List<Double> values : locationWeatherData) {
            valueList.add(values.get(indexOfParameter));
        }
        return valueList;
    }
}
