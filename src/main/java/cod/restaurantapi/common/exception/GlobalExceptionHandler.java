package cod.restaurantapi.common.exception;

import cod.restaurantapi.common.exception.model.RMAError;
import cod.restaurantapi.common.exception.model.RMASubErrors;
import cod.restaurantapi.product.controller.exceptions.CategoryNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentException(final MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        RMASubErrors rmaSubErrors = RMASubErrors.builder()
                .status(HttpStatus.BAD_REQUEST)
                .massage(errorMap)
                .build();

        return new ResponseEntity<>(rmaSubErrors, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException exception) {
        Map<String, String> errorMap = new HashMap<>();
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errorMap.put(field, message);
        }

        RMASubErrors rmaSubErrors = RMASubErrors.builder()
                .massage(errorMap)
                .status(HttpStatus.BAD_REQUEST)
                .build();


        return new ResponseEntity<>(rmaSubErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<RMAError> handleMethodArgumentException(final CategoryNotFoundException exception) {
        RMAError error = RMAError.builder()
                .massage(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
