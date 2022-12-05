package ru.yandex.practicum.filmorate.storage.friends;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
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
class DatabaseFriendStorageTest {

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
        friendStorage.create(2,3);
    }

    @Test
    public void testCreateFriend() {
        friendStorage.create(1,2);
        User user = userStorage.get(1);
        Set<Integer> friendsSet = new HashSet<Integer>();
        friendsSet.add(2);
        assertThat(user).hasFieldOrPropertyWithValue("friends", friendsSet);
    }

    @Test
    public void testDeleteFriend() {
        friendStorage.delete(2,3);
        User user = userStorage.get(2);
        assertThat(user).hasFieldOrPropertyWithValue("friends", new HashSet<Integer>());
    }

    @Test
    void testFindFriends() {
        friendStorage.create(1,2);
        friendStorage.create(1,3);
        User user = userStorage.get(1);
        Set<Integer> friendsSet = new HashSet<Integer>();
        friendsSet.add(2);
        friendsSet.add(3);
        assertThat(user).hasFieldOrPropertyWithValue("friends", friendsSet);
    }
}