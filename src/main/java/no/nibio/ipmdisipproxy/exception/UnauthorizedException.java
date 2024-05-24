package no.nibio.ipmdisipproxy.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends IpmdIsipProxyException {

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, message, cause);
    }
}