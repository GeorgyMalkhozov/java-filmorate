package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addCorrectFilm() throws Exception {
        Film film = new Film(1,"Terminator","Описание фильма",
                LocalDate.of(1982,2,11), 90);
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(film)));
    }

    @Test
    public void addFilmWithWrongDuration() throws Exception {
        Film film = new Film(1,"Terminator","Описание фильма",
                LocalDate.of(1982,2,11), -90);
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400));
    }

    @Test
    public void addFilmWithEmptyName() throws Exception {
        Film film = new Film(1,"","Описание фильма",
                LocalDate.of(1982,2,11), 90);
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400));
    }

    @Test
    public void addFilmWithTooLongDescription() throws Exception {
        Film film = new Film(1,"sdfsdf","Описание фильма11111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111",
                LocalDate.of(1982,2,11), 90);
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400));
    }

    @Test
    public void addFilmWithWrongReleaseDate() throws Exception {
        Film film = new Film(1,"dsfsf","Описание фильма",
                LocalDate.of(1682,2,11), 90);
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400));
    }
}