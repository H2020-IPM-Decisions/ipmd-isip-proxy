package no.nibio.ipmdisipproxy.config;

import no.nibio.ipmdisipproxy.exception.BadRequestException;
import no.nibio.ipmdisipproxy.exception.ExternalApiException;
import no.nibio.ipmdisipproxy.exception.UnauthorizedException;
import no.nibio.ipmdisipproxy.model.Crop;
import no.nibio.ipmdisipproxy.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @since 1.0.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ErrorResponse> handleExternalApiException(ExternalApiException ex) {
        LOGGER.error("External API exception", ex);

        ErrorResponse errorResponse = new ErrorResponse(
                "EXTERNAL_API_ERROR",
                ex.getMessage(),
                ex.getHttpStatus().value()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        LOGGER.error("BadRequestException", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "BAD_REQUEST",
                ex.getMessage(),
                ex.getHttpStatus().value()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format("Invalid value for parameter '%s'. Expected one of [%s].", ex.getName(), Crop.getEppoCodes());
        LOGGER.error(errorMessage, ex);

        ErrorResponse errorResponse = new ErrorResponse(
                "BAD_REQUEST",
                errorMessage,
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        LOGGER.error("Unauthorised exception", ex);

        ErrorResponse errorResponse = new ErrorResponse(
                "UNAUTHORIZED",
                ex.getMessage(),
                ex.getHttpStatus().value()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

}