package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.Data;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.UnknownIdException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Primary
@Repository("databaseMpaStorage")
public class DatabaseMpaStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> findAll() {
        String sql = "SELECT * FROM MPA";
        return jdbcTemplate.query(sql, this::mapRowToMpa);
    }

    @Override
    public Mpa create(Mpa mpa) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("MPA");
        simpleJdbcInsert.execute(convertMpaToDbFormat(mpa));
        return mpa;
    }

    @Override
    public Mpa get(Integer id) {
        checkMpaId(id);
        String mpaRows = "SELECT * FROM MPA WHERE MPA_ID = ?";
        return jdbcTemplate.queryForObject(mpaRows, this::mapRowToMpa, id);
    }

    public Map<String, Object> convertMpaToDbFormat(Mpa mpa) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("MPA_ID", mpa.getId());
        parameters.put("NAME", mpa.getName());
        return parameters;
    }

    private Mpa mapRowToMpa(ResultSet resultSet, int rowNum) throws SQLException {
        return new Mpa(
                resultSet.getInt("MPA_ID"),
                resultSet.getString("NAME"));
    }

    public void checkMpaId(Integer id) {
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet("SELECT * " +
                "FROM MPA " +
                "WHERE MPA_ID = ?", id);
        if(!mpaRows.next()) {throw new UnknownIdException("Указан некорректный Id MPA");}
    }
}
