package no.nibio.ipmdisipproxy.exception;

import org.springframework.http.HttpStatus;

public class ExternalApiException extends IpmdIsipProxyException {

    public ExternalApiException(String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, message);
    }

    public ExternalApiException(String message, Throwable cause) {
        super(HttpStatus.SERVICE_UNAVAILABLE, message, cause);
    }
}
