package ru.yandex.practicum.filmorate.storage.friends;

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
@Repository("databaseFriendStorage")
public class DatabaseFriendStorage implements FriendStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void create(Integer id ,Integer friendId) {
        String sqlQuery = "INSERT INTO FRIENDS SET USER_ID = ?, FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }

    @Override
    public void delete(Integer id ,Integer friendId) {
        String sqlQuery = "DELETE FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }
    @Override
    public boolean isFriendshipExists(Integer userid, Integer friendId){
        SqlRowSet likeRows = jdbcTemplate.queryForRowSet("SELECT * FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?"
                , userid, friendId);
        return likeRows.next();
    }
    @Override
    public Set<Integer> getFriendsOfUser(Integer userId){
        String sql = "SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ?";
        return new HashSet<>(jdbcTemplate.query(sql, this::mapRowToLike, userId));
    }

    private Integer mapRowToLike(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("FRIEND_ID");
    }
}
