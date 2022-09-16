package ru.yandex.practicum.filmorate.exceptions;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerPlus extends ResponseEntityExceptionHandler{

    @ExceptionHandler({ValidationException.class})
    protected ResponseEntity<Object> handleValidationException(ValidationException exception, WebRequest request) {
        log.error(exception.getMessage());
        ApiError apiError = new ApiError("Validation Exception", exception.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({FailedReleaseDateException.class})
    protected ResponseEntity<Object> handleFailedReleaseDateException(FailedReleaseDateException exception,
                                                                      WebRequest request) {
        log.error(exception.getMessage());
        ApiError apiError = new ApiError("FailedReleaseDate Exception", exception.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UnknownIdException.class})
    protected ResponseEntity<Object> handleUnknownIdException(UnknownIdException exception, WebRequest request) {
        log.error(exception.getMessage());
        ApiError apiError = new ApiError("UnknownIdException Exception", exception.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @Override
    protected @NonNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                           @NonNull HttpHeaders headers,
                                                                           @NonNull HttpStatus status,
                                                                           @NonNull WebRequest request) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ApiError apiError = new ApiError("Method Argument Not Valid", exception.getMessage(), errors);
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        log.error(exception.getMessage());
        ApiError apiError = new ApiError("Internal Exception", exception.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
