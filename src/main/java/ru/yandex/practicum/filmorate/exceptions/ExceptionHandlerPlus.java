package ru.yandex.practicum.filmorate.exceptions;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerPlus {

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

    @ExceptionHandler({SqlException.class})
    protected ResponseEntity<Object> handleSqlException(SqlException exception, WebRequest request) {
        log.error(exception.getMessage());
        ApiError apiError = new ApiError("SqlException Exception", exception.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IncorrectCountException.class})
    protected ResponseEntity<Object> handleIncorrectCountException(IncorrectCountException exception,
                                                                   WebRequest request) {
        log.error(exception.getMessage());
        ApiError apiError = new ApiError("IncorrectCountException Exception", exception.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception, WebRequest request) {
        log.error(exception.getMessage());
        ApiError apiError = new ApiError("MethodArgumentTypeMismatchException", exception.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ApiError apiError = new ApiError("Method Argument Not Valid", exception.getMessage(), errors);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected @NonNull ResponseEntity<Object> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {

        ApiError apiError = new ApiError("Поля JSON заполнены некорректно", exception.getMessage());
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        log.error(exception.getMessage());
        ApiError apiError = new ApiError(exception.getClass().toString(), exception.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
