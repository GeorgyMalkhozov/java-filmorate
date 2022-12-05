package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

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
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static List testUserListForStatus400() {
        return List.of(
                new User(1, "mail@mail.ru", "Login", "sdfs",
                        LocalDate.of(2026, 2, 11), new HashSet<Integer>()),
                new User(1,"mailmail.ru","Login","Name",
                        LocalDate.of(1982,2,11), new HashSet<Integer>()),
                new User(1,"","Login","Name",
                        LocalDate.of(1982,2,11), new HashSet<Integer>()),
                new User(1,"mail@mail.ru","","Name",
                        LocalDate.of(1982,2,11), new HashSet<Integer>())
        );
    }

    @ParameterizedTest
    @MethodSource("testUserListForStatus400")
    public void testValidationStatus400(User user) throws Exception {
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
                LocalDate.of(1982,2,11), Set.of(2));
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(406));
    }

    @Test
    public void LoginAsNameWhenNameIsEmpty() throws Exception {

        User user = new User(1,"mail@mail.ru","Login","",
                LocalDate.of(1982,2,11), Set.of(2));
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Login"));
    }

    @Test
    public void addCorrectUser() throws Exception {
        User user = new User(1,"mail@mail.ru","Logi9n","Na9me",
                LocalDate.of(1982,2,11), new HashSet<Integer>());
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }
}