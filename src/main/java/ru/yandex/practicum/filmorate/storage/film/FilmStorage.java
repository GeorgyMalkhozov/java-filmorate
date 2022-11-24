package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;


public interface FilmStorage {
    public List<Film> findAll();

    public Film create(Film film);

    public Film put(Film film);

    public Film get(Integer id);

    public List<Film> getPopularFilms(int count);

    public boolean isNewFilm(Film film);

    public void checkFilmId(Integer id);
}
