package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FailedReleaseDateException;
import ru.yandex.practicum.filmorate.exceptions.UnknownIdException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private static int filmIdGenerator;
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> findAll() {
        log.debug("Получен запрос GET /films.");
        return new ArrayList<Film>(films.values());
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.debug("Получен запрос POST /film.");
        checkReleaseDate(film);
        if (!isNewFilm(film)) {throw new ValidationException("Фильм уже есть в базе");}
        film.setId(generateNewFilmId());
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) {
        log.debug("Получен запрос POST /film.");
        if (!films.containsKey(film.getId())) {throw new UnknownIdException("Указан некорректный Id фильма");}
        checkReleaseDate(film);
        if (film.getId() == null) {film.setId(generateNewFilmId());}
        films.put(film.getId(),film);
        return film;
    }

    private int generateNewFilmId(){
        filmIdGenerator++;
        return filmIdGenerator;
    }

    private void checkReleaseDate(Film film){
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new FailedReleaseDateException("Дата релиза должна быть не ранее 28 декабря 1895 года");
        }
    }

    private boolean isNewFilm(Film film){
        return !films.containsValue(film);
    }
}
