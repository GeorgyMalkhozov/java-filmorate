package ru.yandex.practicum.filmorate.storage.genre;

import lombok.Data;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.UnknownIdException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Data
@Primary
@Repository("databaseGenreStorage")
public class DatabaseGenreStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> findAll() {
        String sql = "SELECT * FROM GENRES";
        return jdbcTemplate.query(sql, this::mapRowToGenre);
    }

    @Override
    public Genre create(Genre genre) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("GENRES");
        simpleJdbcInsert.execute(convertGenreToDbFormat(genre));
        return genre;
    }

    @Override
    public Genre get(Integer id) {
        checkGenreId(id);
        String genreRows = "SELECT * FROM GENRES WHERE GENRE_ID = ?";
        return jdbcTemplate.queryForObject(genreRows, this::mapRowToGenre, id);
    }

    public Map<String, Object> convertGenreToDbFormat(Genre genre) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("GENRE_ID", genre.getId());
        parameters.put("GENRE_NAME", genre.getName());
        return parameters;
    }

    public Set<Genre> getGenresOfFilm(Integer filmId) {
        String sql = "SELECT FG.GENRE_ID, GE.NAME " +
                "FROM FILM_GENRE FG " +
                "LEFT JOIN GENRES GE ON FG.GENRE_ID=GE.GENRE_ID " +
                "WHERE FILM_ID = ?";
        return new HashSet<>(jdbcTemplate.query(sql, this::mapRowToGenre, filmId));
    }

    public Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return new Genre(
                resultSet.getInt("GENRE_ID"),
                resultSet.getString("NAME"));
    }

    public void checkGenreId(Integer id) {
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("SELECT * " +
                "FROM GENRES " +
                "WHERE GENRE_ID = ?", id);
        if(!genreRows.next()) {throw new UnknownIdException("Указан некорректный Id жанра");}
    }
}
