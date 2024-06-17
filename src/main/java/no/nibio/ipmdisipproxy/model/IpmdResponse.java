package no.nibio.ipmdisipproxy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.0.0
 */
public class IpmdResponse {

    private String timeStart;
    private String timeEnd;
    private int interval = 86400;
    private List<String> resultParameters;
    private List<IpmdLocationResult> locationResult;
    private String message;
    private Integer messageType;

    public IpmdResponse() {

    }

    public IpmdResponse(String timeStart, String timeEnd, double latitude, double longitude) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.resultParameters = new ArrayList<>();
        IpmdLocationResult lr = new IpmdLocationResult();
        lr.setLatitude(latitude);
        lr.setLongitude(longitude);
        lr.setWarningStatus(new ArrayList<>());
        locationResult = new ArrayList<>();
        locationResult.add(lr);
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public List<String> getResultParameters() {
        return resultParameters;
    }

    public void setResultParameters(List<String> resultParameters) {
        this.resultParameters = resultParameters;
    }

    public List<IpmdLocationResult> getLocationResult() {
        return locationResult;
    }

    public void setLocationResult(List<IpmdLocationResult> locationResult) {
        this.locationResult = locationResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }
}