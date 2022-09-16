package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void LoginAsNameWhenNameIsEmpty() throws Exception {

        User user = new User(1,"mail@mail.ru","Login","",
                LocalDate.of(1982,2,11));
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Login"));
    }

    @Test
    public void addCorrectFilm() throws Exception {
        User user = new User(2,"mail@mail.ru","Login","Name",
                LocalDate.of(1982,2,11));
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    public void addWrongEmail() throws Exception {
        User user = new User(1,"mailmail.ru","Login","Name",
                LocalDate.of(1982,2,11));
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400));
    }

    @Test
    public void addEmptyEmail() throws Exception {
        User user = new User(1,"","Login","Name",
                LocalDate.of(1982,2,11));
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400));
    }

    @Test
    public void addEmptyLogin() throws Exception {
        User user = new User(1,"mail@mail.ru","","Name",
                LocalDate.of(1982,2,11));
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400));
    }

    @Test
    public void addLoginWithSpaces() throws Exception {
        User user = new User(1,"mail@mail.ru","sdf sdfs","Name",
                LocalDate.of(1982,2,11));
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(406));
    }

    @Test
    public void BirthDateInTheFuture() throws Exception {
        User user = new User(1,"mail@mail.ru","Login","sdfs",
                LocalDate.of(2026,2,11));
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400));
    }
}