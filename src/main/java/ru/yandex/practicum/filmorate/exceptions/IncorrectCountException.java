package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectCountException extends ResponseStatusException {
    public IncorrectCountException(String message) {
        super(HttpStatus.BAD_REQUEST, message);;
    }
}
