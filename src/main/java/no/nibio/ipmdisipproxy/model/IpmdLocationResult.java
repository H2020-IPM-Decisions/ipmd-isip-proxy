package no.nibio.ipmdisipproxy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IpmdLocationResult {
    private Double longitude;
    private Double latitude;
    private Double altitude;
    private List<List<Double>> data;
    private List<Integer> warningStatus;

    public IpmdLocationResult() {
        data = new ArrayList<>();
        warningStatus = new ArrayList<>();
    }

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

    public List<List<Double>> getData() {
        return data;
    }

    public void setData(List<List<Double>> data) {
        this.data = data;
    }

    public List<Integer> getWarningStatus() {
        return warningStatus;
    }

    public void setWarningStatus(List<Integer> warningStatus) {
        this.warningStatus = warningStatus;
    }

    public Integer getWidth() {
        return 0;
    }

    public Integer getLength() {
        return getWarningStatus().size();
    }

}
