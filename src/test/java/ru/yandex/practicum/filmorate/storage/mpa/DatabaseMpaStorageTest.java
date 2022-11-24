package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

class DatabaseMpaStorageTest {

    @Autowired
    private final MpaStorage mpaStorage;

    @Test
    void findAll() {
        List<Mpa> mpa = mpaStorage.findAll();
        assertThat(mpa).hasSize(5);
    }

    @Test
    void get() {
        Mpa mpa = mpaStorage.get(1);
        assertThat(mpa).hasFieldOrPropertyWithValue("name", "G");
    }
}