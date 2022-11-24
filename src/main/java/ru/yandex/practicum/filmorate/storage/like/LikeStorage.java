package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.data.relational.core.sql.In;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Set;

public interface LikeStorage {

    public void create(Integer filmId, Integer userId);

    public void deleteLike(Integer filmId, Integer userId);

    public boolean isLikeExists(Integer filmId, Integer userId);

    public Set<Integer> getLikesOfFilm(Integer filmId);



}
