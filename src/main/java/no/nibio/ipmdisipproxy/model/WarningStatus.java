package no.nibio.ipmdisipproxy.model;

import no.nibio.ipmdisipproxy.exception.ExternalApiException;

public enum WarningStatus {

    OFF_SEASON(0, 0),
    INFECTION_IMPROBABLE(2, 1),
    INFECTION_POSSIBLE(3, 2),
    INFECTION_PROBABLE(4, 3);
    private int ipmCode;
    private int isipCode;

    WarningStatus(int ipmCode, int isipCode) {
        this.ipmCode = ipmCode;
        this.isipCode = isipCode;
    }

    public static WarningStatus fromIsipCode(int isipCode) {
        for (WarningStatus ws : WarningStatus.values()) {
            if (ws.getIsipCode() == isipCode) {
                return ws;
            }
        }
        throw new ExternalApiException("Response contains unknown warning status: " + isipCode);
    }


    public int getIpmCode() {
        return ipmCode;
    }

    public int getIsipCode() {
        return isipCode;
    }
}