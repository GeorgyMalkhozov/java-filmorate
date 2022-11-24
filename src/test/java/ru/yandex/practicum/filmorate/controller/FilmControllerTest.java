package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static List testFilmListForStatus400() {
        return List.of(
                new Film(1,"dsfsf","Описание фильма",
                        LocalDate.of(1682,2,11), 90,3, new HashSet<Integer>(),
                        new Mpa(1,"G"), Set.of(new Genre(1, "Комедия"))),
                new Film(1,"","Описание фильма",
                        LocalDate.of(1982,2,11), 90,3, new HashSet<Integer>(),
                        new Mpa(1,"G"), Set.of(new Genre(1, "Комедия"))),
                new Film(1,"Terminator","Описание фильма",
                        LocalDate.of(1982,2,11), -90,3, new HashSet<Integer>(),
                        new Mpa(1,"G"), Set.of(new Genre(1, "Комедия"))),
                new Film(1,"sdfsdf","Описание фильма11111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "1111111111111111111111111111",
                        LocalDate.of(1982,2,11), 90,3, new HashSet<Integer>(),
                        new Mpa(1,"G"), Set.of(new Genre(1, "Комедия"))));
    }

    @ParameterizedTest
    @MethodSource("testFilmListForStatus400")
    public void testValidationStatus400(Film film) throws Exception {
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400));
    }

    @Test
    public void addCorrectFilm() throws Exception {
        Film film = new Film(1,"Terminator","Описание фильма",
                LocalDate.of(1982,2,11), 90,3, new HashSet<Integer>(),
                new Mpa(1,"G"), Set.of(new Genre(1, "Комедия")));
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(film)));
    }
}
