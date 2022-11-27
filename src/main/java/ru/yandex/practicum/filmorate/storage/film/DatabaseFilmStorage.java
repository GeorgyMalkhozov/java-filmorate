package ru.yandex.practicum.filmorate.storage.film;

import lombok.Data;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.UnknownIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Data
@Primary
@Repository("databaseFilmStorage")
public class DatabaseFilmStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;

    private final GenreStorage genreStorage;
    private final LikeStorage likeStorage;

    @Override
    public List<Film> findAll() {
        String sql = "SELECT * FROM FILMS";
        return jdbcTemplate.query(sql, this::mapRowToFilm);
    }

    @Override
    public Film create(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FILMS")
                .usingGeneratedKeyColumns("FILM_ID");
        int newFilmId = simpleJdbcInsert.executeAndReturnKey(convertFilmToDbFormat(film)).intValue();
        film.setId(newFilmId);
        saveGenres(film);
        return get(film.getId());
    }

    @Override
    public Film put(Film film) {
        get(film.getId());
        String sqlQuery = "UPDATE FILMS " +
                "SET NAME = ?, " +
                "DESCRIPTION = ?, " +
                "RELEASE_DATE = ?, " +
                "RATE = ?, " +
                "DURATION = ?, " +
                "MPA_ID = ?" +
                "WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery
                , film.getName()
                , film.getDescription()
                , film.getReleaseDate()
                , film.getRate()
                , film.getDuration()
                , film.getMpa().getId()
                , film.getId());
        saveGenres(film);
        return get(film.getId());
    }

    @Override
    public Film get(Integer id) {
        String filmRows = "SELECT * FROM FILMS F WHERE FILM_ID = ?";
        return jdbcTemplate.queryForObject(filmRows, this::mapRowToFilm, id);
    }

    @Override
    public List<Film> getPopularFilms(int count){
        String sql = "SELECT * " +
                "FROM FILMS F " +
                "LEFT JOIN (" +
                            "SELECT DISTINCT FILM_ID, " +
                            "COUNT(USER_ID) AS LIKECOUNT " +
                            "FROM LIKES " +
                            "GROUP BY FILM_ID) AS LIKETEMP on F.FILM_ID = LIKETEMP.FILM_ID " +
                "ORDER BY LIKETEMP.LIKECOUNT DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sql, this::mapRowToFilm, count);
    }

    public Map<String, Object> convertFilmToDbFormat(Film film) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("FILM_ID", film.getId());
        parameters.put("NAME", film.getName());
        parameters.put("DESCRIPTION", film.getDescription());
        parameters.put("RELEASE_DATE", film.getReleaseDate());
        parameters.put("RATE", film.getRate());
        parameters.put("DURATION", film.getDuration());
        parameters.put("MPA_ID", film.getMpa().getId());
        return parameters;
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return new Film(
                resultSet.getInt("FILM_ID"),
                resultSet.getString("NAME"),
                resultSet.getString("DESCRIPTION"),
                resultSet.getDate("RELEASE_DATE").toLocalDate(),
                resultSet.getInt("DURATION"),
                resultSet.getInt("RATE"),
                likeStorage.getLikesOfFilm(resultSet.getInt("FILM_ID")),
                mpaStorage.get(resultSet.getInt("MPA_ID")),
                genreStorage.getGenresOfFilm(resultSet.getInt("FILM_ID")));
    }

    private void saveGenres(Film film) {
        jdbcTemplate.update("DELETE FROM FILM_GENRE WHERE FILM_ID = ?", film.getId());
        if (film.getGenres()==null) {return;}
        final List<Genre> genres = new ArrayList<>(film.getGenres());
        jdbcTemplate.batchUpdate("INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES (?, ?)",
            new BatchPreparedStatementSetter() {

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, film.getId());
                    ps.setInt(2, genres.get(i).getId());
                }
                public int getBatchSize() {return genres.size();}
        });
    }

    @Override
    public boolean isNewFilm(Film film){
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT * " +
                "FROM FILMS F " +
                "WHERE NAME = ? AND DURATION = ? AND RELEASE_DATE = ?",
                film.getName(), film.getDuration(), film.getReleaseDate());
        return !filmRows.next();
    }

    @Override
    public void checkFilmId(Integer id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT * " +
                "FROM FILMS F " +
                "WHERE FILM_ID = ?", id);
        if(!filmRows.next()) {throw new UnknownIdException("Указан некорректный Id фильма");}
    }
}
