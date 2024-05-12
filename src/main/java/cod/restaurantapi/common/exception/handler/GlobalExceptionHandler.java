package cod.restaurantapi.common.exception.handler;

import cod.restaurantapi.common.exception.RMANotFoundException;
import cod.restaurantapi.common.exception.model.RMAError;
import cod.restaurantapi.category.controller.exceptions.CategoryNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RMAError> handleMethodArgumentException(
            final MethodArgumentNotValidException exception) {

        log.error(exception.getMessage(), exception);

        RMAError error = RMAError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errors(new ArrayList<>())
                .build();

        exception.getBindingResult().getFieldErrors().forEach(fieldError
                -> error.getErrors().add(RMAError.SubErrors.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build()));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RMAError> handleIllegalArgumentException(
            final IllegalArgumentException exception) {
        log.error(exception.getMessage(), exception);


        RMAError error = RMAError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RMAError> handleConstraintViolationException(
            final ConstraintViolationException exception) {

        log.error(exception.getMessage(), exception);

        RMAError error = RMAError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errors(new ArrayList<>())
                .build();

        exception.getConstraintViolations().forEach(constraintViolation
                -> error.getErrors().add(RMAError.SubErrors.builder()
                .field(constraintViolation.getPropertyPath().toString())
                .message(constraintViolation.getMessage())
                .build()));


        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RMANotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RMAError> handleMethodArgumentException(
            final CategoryNotFoundException exception) {

        log.error(exception.getMessage(), exception);

        RMAError error = RMAError.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<RMAError> handleIOException(final IOException exception) {

        log.error(exception.getMessage(), exception);

        RMAError error = RMAError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RMAError> handleException(final Exception exception) {

        log.error(exception.getMessage(), exception);

        RMAError error = RMAError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Process Error")
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
