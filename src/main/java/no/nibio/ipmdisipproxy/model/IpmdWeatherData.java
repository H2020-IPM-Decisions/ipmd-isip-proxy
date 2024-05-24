package no.nibio.ipmdisipproxy.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;
import java.util.List;

public class IpmdWeatherData {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime timeStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime timeEnd;
    private Integer interval;
    private List<Integer> weatherParameters;
    private List<IpmdLocationWeatherData> locationWeatherData;

    public ZonedDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(ZonedDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public ZonedDateTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(ZonedDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public List<Integer> getWeatherParameters() {
        return weatherParameters;
    }

    public void setWeatherParameters(List<Integer> weatherParameters) {
        this.weatherParameters = weatherParameters;
    }

    public List<IpmdLocationWeatherData> getLocationWeatherData() {
        return locationWeatherData;
    }

    public void setLocationWeatherData(List<IpmdLocationWeatherData> locationWeatherData) {
        this.locationWeatherData = locationWeatherData;
    }
}
