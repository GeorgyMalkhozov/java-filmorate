package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
public class FilmController {

    @Qualifier("inMemoryFilmStorage")
    private final FilmService filmService;
    @Autowired
    public FilmController(FilmService filmService) {this.filmService = filmService;}

    @GetMapping("/films")
    public List<Film> findAll() {
        log.debug("Получен запрос GET /films.");
        return filmService.findAll();
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) {
        log.debug("Получен запрос POST /film.");
        return filmService.create(film);
    }

    @PutMapping("/films")
    public Film put(@Valid @RequestBody Film film) {
        log.debug("Получен запрос UPDATE /film.");
        return filmService.put(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.debug("Получен запрос на добавление лайка");
        filmService.addLike(id,userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void removeLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.debug("Получен запрос на добавление лайка");
        filmService.deleteLike(id,userId);
    }

    @GetMapping("/films/popular")
    public List<Film> showPopularFilms(@RequestParam (defaultValue = "2") int count) {
        log.debug("Получен запрос GET /films/popular.");
        return filmService.getPopularFilms(count);
    }

    @GetMapping("/films/{id}")
    public Film get(@PathVariable Integer id) {
        log.debug("Получен запрос GET /films/film.");
        return filmService.get(id);
    }
}
