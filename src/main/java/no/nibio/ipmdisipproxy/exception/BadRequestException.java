package no.nibio.ipmdisipproxy.exception;

import org.springframework.http.HttpStatus;

/**
 * @since 1.0.0
 */
public class BadRequestException extends IpmdIsipProxyException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
