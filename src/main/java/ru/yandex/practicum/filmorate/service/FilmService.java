package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FailedReleaseDateException;
import ru.yandex.practicum.filmorate.exceptions.IncorrectCountException;
import ru.yandex.practicum.filmorate.exceptions.UnknownIdException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class FilmService{
    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
    }

    public void addLike(Integer filmId, Integer userId) {
        likeStorage.create(filmId, userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        if (!likeStorage.isLikeExists(filmId,userId)) {
            throw new UnknownIdException("У фильма нет лайка от пользователя");
        }
        likeStorage.deleteLike(filmId,userId);
    }

    public List<Film> getPopularFilms(int count) {
        if (count <= 0) {
            throw new IncorrectCountException("Параметр count должен быть положительным.");
        }
       return filmStorage.getPopularFilms(count);
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    };

    public Film create(Film film) {
        checkReleaseDate(film);
        if (!filmStorage.isNewFilm(film)) {throw new ValidationException("Фильм уже есть в базе");}
        return filmStorage.create(film);
    };

    public Film put(Film film) {
        filmStorage.checkFilmId(film.getId());
        checkReleaseDate(film);
        return filmStorage.put(film);
    };

    public Film get(Integer id) {
        filmStorage.checkFilmId(id);
        return filmStorage.get(id);
    };

    private void checkReleaseDate(Film film){
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new FailedReleaseDateException("Дата релиза должна быть не ранее 28 декабря 1895 года");
        }
    }
}
