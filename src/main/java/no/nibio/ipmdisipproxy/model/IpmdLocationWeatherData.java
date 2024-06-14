package no.nibio.ipmdisipproxy.model;

import java.util.List;

/**
 * @since 1.0.0
 */
public class IpmdLocationWeatherData {
    private Double longitude;
    private Double latitude;
    private Double altitude;
    private List<Integer> qc;
    private List<Integer> amalgamation;
    private List<List<Double>> data;
    private Integer length;
    private Integer width;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public List<Integer> getQc() {
        return qc;
    }

    public void setQc(List<Integer> qc) {
        this.qc = qc;
    }

    public List<Integer> getAmalgamation() {
        return amalgamation;
    }

    public void setAmalgamation(List<Integer> amalgamation) {
        this.amalgamation = amalgamation;
    }

    public List<List<Double>> getData() {
        return data;
    }

    public void setData(List<List<Double>> data) {
        this.data = data;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
