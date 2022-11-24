package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DatabaseUserStorageTest{
    @Autowired
    private final UserStorage userStorage;
    @Autowired
    private final FriendStorage friendStorage;

    @BeforeEach
    void setUp() {
        userStorage.create(new User(1, "mail@mail.ru", "Login", "sdfs",
                LocalDate.of(2000, 2, 11), new HashSet<Integer>()));
        userStorage.create(new User(2, "mai5l@mail.ru", "L21ogin", "s5dfs",
                LocalDate.of(2000, 2, 11), new HashSet<Integer>()));
        userStorage.create(new User(3, "ma2il@mail.ru", "L2ogin", "sd2fs",
                LocalDate.of(2000, 2, 11), new HashSet<Integer>()));
    }

    @Test
    public void testFindUserById() {
        User user = userStorage.get(1);
        assertThat(user).hasFieldOrPropertyWithValue("id",1);
    }

    @Test
    public void testFindAllUsers() {
        List<User> users = userStorage.findAll();
        assertThat(users).hasSize(3);
    }

    @Test
    void testCreateUser() {
        userStorage.create(new User(4, "ma221il@mail.ru", "L244ogin", "s44d2fs",
                LocalDate.of(2000, 2, 11), new HashSet<Integer>()));
        User user = userStorage.get(4);
        assertThat(user).hasFieldOrPropertyWithValue("name","s44d2fs");
    }

   @Test
    void testPutUser() {
        userStorage.put(new User(3, "ma@mail.ru", "L244ogin", "d2fs",
                LocalDate.of(2000, 2, 11), new HashSet<Integer>()));
       User user = userStorage.get(3);
       assertThat(user).hasFieldOrPropertyWithValue("name","d2fs");
    }
}