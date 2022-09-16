package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FailedReleaseDateException extends ResponseStatusException {
    public FailedReleaseDateException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
