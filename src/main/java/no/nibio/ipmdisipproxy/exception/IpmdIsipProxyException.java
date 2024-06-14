package no.nibio.ipmdisipproxy.exception;

import org.springframework.http.HttpStatus;

/**
 * @since 1.0.0
 */
public abstract class IpmdIsipProxyException extends RuntimeException {

    private final HttpStatus httpStatus;

    public IpmdIsipProxyException(HttpStatus status, String message) {
        super(message);
        this.httpStatus = status;
    }

    public IpmdIsipProxyException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
