package ru.yandex.practicum.filmorate.storage.like;

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
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DatabaseLikeStorageTest {

    @Autowired
    private final LikeStorage likeStorage;
    @Autowired
    private final FilmStorage filmStorage;
    @Autowired
    private final UserStorage userStorage;

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
        userStorage.create(new User(1, "mail@mail.ru", "Login", "sdfs",
                LocalDate.of(2000, 2, 11), new HashSet<Integer>()));
        userStorage.create(new User(2, "mai5l@mail.ru", "L21ogin", "s5dfs",
                LocalDate.of(2000, 2, 11), new HashSet<Integer>()));
        userStorage.create(new User(3, "ma2il@mail.ru", "L2ogin", "sd2fs",
                LocalDate.of(2000, 2, 11), new HashSet<Integer>()));
        likeStorage.create(3,3);
    }

    @Test
    public void testCreateLike() {
        likeStorage.create(1,2);
        Set<Integer> likesSet = new HashSet<Integer>();
        likesSet.add(2);
        assertThat(filmStorage.get(1)).hasFieldOrPropertyWithValue("likes", likesSet);
    }

    @Test
    public void testDeleteLike() {
        likeStorage.deleteLike(3, 3);
        Film film = filmStorage.get(3);
        assertThat(film).hasFieldOrPropertyWithValue("likes", new HashSet<Integer>());
    }
}