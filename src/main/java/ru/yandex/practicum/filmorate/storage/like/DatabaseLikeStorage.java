package ru.yandex.practicum.filmorate.storage.like;

import lombok.Data;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Data
@Primary
@Repository("databaseLikeStorage")
public class DatabaseLikeStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void create(Integer filmId, Integer userId) {
        String sqlQuery = "INSERT INTO LIKES SET USER_ID = ?, FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, userId, filmId);
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        String sqlQuery = "DELETE FROM LIKES WHERE USER_ID = ? AND FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, userId, filmId);
    }

    public boolean isLikeExists(Integer filmId, Integer userId){
        SqlRowSet likeRows = jdbcTemplate.queryForRowSet("SELECT * FROM LIKES WHERE USER_ID = ? AND FILM_ID = ?"
                , userId, filmId);
        return likeRows.next();
    }

    public Set<Integer> getLikesOfFilm(Integer filmId){
        String sql = "SELECT USER_ID FROM LIKES WHERE FILM_ID = ?";
        return new HashSet<>(jdbcTemplate.query(sql, this::mapRowToLike, filmId));
    }

    private Integer mapRowToLike(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("USER_ID");
    }
}
