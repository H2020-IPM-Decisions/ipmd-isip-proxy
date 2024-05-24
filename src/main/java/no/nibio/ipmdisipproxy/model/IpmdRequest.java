package no.nibio.ipmdisipproxy.model;

public class IpmdRequest {

    private String modelId;
    private String crop;
    private String timeZone;

    private IpmdWeatherData weatherData;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public IpmdWeatherData getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(IpmdWeatherData weatherData) {
        this.weatherData = weatherData;
    }

}
