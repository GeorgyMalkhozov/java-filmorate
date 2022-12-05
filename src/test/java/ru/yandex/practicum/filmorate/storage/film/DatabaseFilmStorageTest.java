package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DatabaseFilmStorageTest {

    @Autowired
    private final FilmStorage filmStorage;

    @BeforeEach
    void setUp() {
        filmStorage.create(new Film(1,"dsfs4f","Описание фильма",
                LocalDate.of(1982,2,11), 90,3, new HashSet<Integer>(),
                new Mpa(1,"G"), Set.of(new Genre(1, "Комедия"))));
        filmStorage.create(new Film(2,"dsfs3f","Описание фильма",
                LocalDate.of(1982,2,11), 90,3, new HashSet<Integer>(),
                new Mpa(1,"G"), Set.of(new Genre(1, "Комедия"))));
        filmStorage.create(new Film(3,"dsf56sf","Описание фильма",
                LocalDate.of(1982,2,11), 90,3, new HashSet<Integer>(),
                new Mpa(1,"G"), Set.of(new Genre(1, "Комедия"))));
    }

    @Test
    public void testFindFilmById() {
        Film film = filmStorage.get(1);
        assertThat(film).hasFieldOrPropertyWithValue("id",1);
    }

    @Test
    public void testFindAllFilms() {
        List<Film> films = filmStorage.findAll();
        assertThat(films).hasSize(3);
    }

    @Test
    void testCreateFilm() {
        filmStorage.create(new Film(4,"dsf56233sf","Описание фильма",
                LocalDate.of(1982,2,11), 90,3, new HashSet<Integer>(),
                new Mpa(1,"G"), Set.of(new Genre(1, "Комедия"))));
        Film film = filmStorage.get(4);
        assertThat(film).hasFieldOrPropertyWithValue("name","dsf56233sf");
    }

    @Test
    void testPutFilm() {
        filmStorage.put(new Film(3,"d4","Описание фильма",
                LocalDate.of(1982,2,11), 90,3, new HashSet<Integer>(),
                new Mpa(1,"G"), Set.of(new Genre(1, "Комедия"))));
        Film film = filmStorage.get(3);
        assertThat(film).hasFieldOrPropertyWithValue("name","d4");
    }
}