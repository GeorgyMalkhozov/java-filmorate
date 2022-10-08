package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.IncorrectCountException;
import ru.yandex.practicum.filmorate.exceptions.UnknownIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService{
    @Qualifier("inMemoryFilmStorage")
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(Integer filmId, Integer userId) {
       filmStorage.get(filmId).addLike(userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        if (!filmStorage.get(filmId).getLikes().contains(userId)) {
            throw new UnknownIdException("У фильма нет лайка от пользователя");
        }
        filmStorage.get(filmId).deleteLike(userId);
    }

    public List<Film> getPopularFilms(int count) {
        if (count <= 0) {
            throw new IncorrectCountException("Параметр count должен быть положительным.");
        }
        return filmStorage.findAll().stream()
                .sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    };

    public Film create(Film film) {
        return filmStorage.create(film);
    };

    public Film put(Film film) {
        return filmStorage.put(film);
    };

    public Film get(Integer id) {
        return filmStorage.get(id);
    };
    private int compare(Film film0, Film film1) {
        Integer film1LikesCount = film1.getLikes().size();
        return film1LikesCount.compareTo(film0.getLikes().size());
    }
}
