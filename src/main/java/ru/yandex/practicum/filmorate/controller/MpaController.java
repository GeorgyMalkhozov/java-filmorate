package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
public class MpaController {

    private final MpaService mpaService;

    @Autowired
    public MpaController(MpaService mpaService) {this.mpaService = mpaService;}

    @GetMapping
    public List<Mpa> findAll() {
        log.debug("Получен запрос GET /mpa.");
        return mpaService.findAll();
    }

    @GetMapping("/{id}")
    public Mpa get(@PathVariable Integer id) {
        log.debug("Получен запрос GET /mpa/mpa.");
        return mpaService.get(id);
    }
}
