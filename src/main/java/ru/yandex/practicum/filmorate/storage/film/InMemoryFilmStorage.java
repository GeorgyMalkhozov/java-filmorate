package ru.yandex.practicum.filmorate.storage.film;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FailedReleaseDateException;
import ru.yandex.practicum.filmorate.exceptions.UnknownIdException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {

    private static int filmIdGenerator;

    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public List<Film> findAll() {
        return new ArrayList<Film>(films.values());
    }

    @Override
    public Film create(Film film) {
        checkReleaseDate(film);
        if (!isNewFilm(film)) {throw new ValidationException("Фильм уже есть в базе");}
        film.setId(generateNewFilmId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film put(Film film) {
        checkFilmId(film.getId());
        checkReleaseDate(film);
        films.put(film.getId(),film);
        return film;
    }

    @Override
    public Film get(Integer id) {
        checkFilmId(id);
        return films.get(id);
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

    private void checkFilmId(Integer id) {
        if (!films.containsKey(id)) {throw new UnknownIdException("Указан некорректный Id фильма");}
    }
}
