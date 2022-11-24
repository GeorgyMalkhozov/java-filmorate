package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.DatabaseMpaStorage;

import java.util.List;

@Slf4j
@Service
public class MpaService {
    private final DatabaseMpaStorage mpaStorage;

    @Autowired
    public MpaService(DatabaseMpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<Mpa> findAll() {
        return mpaStorage.findAll();
    };

    public Mpa create(Mpa mpa) {
        return mpaStorage.create(mpa);
    };

    public Mpa get(Integer id) {
        return mpaStorage.get(id);
    };
}
