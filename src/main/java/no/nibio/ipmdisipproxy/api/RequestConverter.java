package no.nibio.ipmdisipproxy.api;

import no.nibio.ipmdisipproxy.exception.BadRequestException;
import no.nibio.ipmdisipproxy.model.*;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestConverter {

    /**
     * Convert request from IPM Decisions format to ISIP format
     *
     * @param crop           The crop to run model for
     * @param longitude      The longitude
     * @param latitude       The latitude
     * @param simulationDate The simulation date, YYYY-MM-DD
     * @param weatherData    The weather data in IPMD format
     * @return The ISIP request
     */
    public static IsipRequest ipmToIsipRequest(Crop crop, Disease disease, Double longitude, Double latitude, String simulationDate, IpmdWeatherData weatherData) {
        IpmdLocationWeatherData locationWeatherData = weatherData.getLocationWeatherData().get(0);
        List<Integer> weatherParameters = weatherData.getWeatherParameters();
        List<List<Double>> weatherDataValues = locationWeatherData.getData();

        IsipRequest isipRequest = new IsipRequest(longitude, latitude, simulationDate, crop.getIsipName(), disease.getIsipName());
        isipRequest.getVariables().setDateTime(convertDateTimeList(weatherData.getTimeStart(), weatherData.getTimeEnd(), weatherData.getInterval()));
        isipRequest.getVariables().setT2m(convertWeatherDataParameterValues(weatherParameters, weatherDataValues, WeatherDataParameter.TEMPERATURE_INSTANT, WeatherDataParameter.TEMPERATURE_MEAN));
        isipRequest.getVariables().setTotPrec(convertWeatherDataParameterValues(weatherParameters, weatherDataValues, WeatherDataParameter.PRECIPITATION));
        isipRequest.getVariables().setRelhum2m(convertWeatherDataParameterValues(weatherParameters, weatherDataValues, WeatherDataParameter.HUMIDITY_INSTANT, WeatherDataParameter.HUMIDITY_MEAN));
        return isipRequest;
    }


    public static List<String> convertDateTimeList(ZonedDateTime timeStart, ZonedDateTime timeEnd, Integer interval) {
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

    public static List<Double> convertWeatherDataParameterValues(List<Integer> weatherParameters, List<List<Double>> locationWeatherData, WeatherDataParameter... parameters) {
        int indexOfParameter = -1;
        for (WeatherDataParameter parameter : parameters) {
            if (weatherParameters.contains(parameter.getIpmCode())) {
                indexOfParameter = weatherParameters.indexOf(parameter.getIpmCode());
                break;
            }
        }
        if (indexOfParameter < 0) {
            String ipmCodesString = Stream.of(parameters).map(WeatherDataParameter::getIpmCode).map(String::valueOf).collect(Collectors.joining("/"));
            throw new BadRequestException(String.format("Required weather data parameter [%s] missing from request", ipmCodesString));
        }

        List<Double> valueList = new ArrayList<>();
        for (List<Double> values : locationWeatherData) {
            valueList.add(values.get(indexOfParameter));
        }
        return valueList;
    }
}
