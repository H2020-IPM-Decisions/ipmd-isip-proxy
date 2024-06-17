package no.nibio.ipmdisipproxy.model;

/**
 * @since 1.0.0
 */
public enum WeatherDataParameter {

    TEMPERATURE_INSTANT(1001, "t_2m"),
    TEMPERATURE_MEAN(1002, "t_2m"),
    PRECIPITATION(2001, "tot_prec"),
    HUMIDITY_INSTANT(3001, "relhum_2m"),
    HUMIDITY_MEAN(3002, "relhum_2m");

    private final int ipmCode;
    private final String isipCode;

    WeatherDataParameter(int ipmCode, String isipCode) {
        this.ipmCode = ipmCode;
        this.isipCode = isipCode;
    }

    public int getIpmCode() {
        return ipmCode;
    }

    public String getIsipCode() {
        return isipCode;
    }
}
