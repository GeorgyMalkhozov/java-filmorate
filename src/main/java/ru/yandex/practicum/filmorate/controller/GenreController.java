package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {this.genreService = genreService;}

    @GetMapping
    public List<Genre> findAll() {
        log.debug("Получен запрос GET /genres.");
        return genreService.findAll();
    }

    @GetMapping("/{id}")
    public Genre get(@PathVariable Integer id) {
        log.debug("Получен запрос GET /genres/genre.");
        return genreService.get(id);
    }
}
