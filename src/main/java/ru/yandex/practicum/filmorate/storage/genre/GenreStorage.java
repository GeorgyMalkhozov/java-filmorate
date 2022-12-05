package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;


public interface GenreStorage {
    public List<Genre> findAll();

    public Genre create(Genre genre);

    public Genre get(Integer id);

    public Set<Genre> getGenresOfFilm(Integer filmId);
}
