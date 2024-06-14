package no.nibio.ipmdisipproxy.exception;

import org.springframework.http.HttpStatus;

/**
 * @since 1.0.0
 */
public class ExternalApiException extends IpmdIsipProxyException {

    public ExternalApiException(String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, message);
    }

    public ExternalApiException(String message, Throwable cause) {
        super(HttpStatus.SERVICE_UNAVAILABLE, message, cause);
    }
}
