package ru.yandex.practicum.filmorate.storage.user;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.UnknownIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Data
@Primary
@Repository("databaseUserStorage")
public class DatabaseUserStorage implements UserStorage{

    private final JdbcTemplate jdbcTemplate;

    @Qualifier("databaseFriendStorage")
    private final FriendStorage friendStorage;

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM USERS";
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    @Override
    public User create(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("USER_ID");
        return get(simpleJdbcInsert.executeAndReturnKey(convertUserToDbFormat(user)).intValue());
    }

    @Override
    public User put(User user) {
        String sqlQuery = "UPDATE USERS " +
                "SET NAME = ?, " +
                "LOGIN = ?, " +
                "EMAIL = ?, " +
                "BIRTHDAY = ?" +
                "WHERE USER_ID = ?";
        jdbcTemplate.update(sqlQuery
                , user.getName()
                , user.getLogin()
                , user.getEmail()
                , user.getBirthday()
        ,user.getId());
        return user; // переделать return - сделать вызов изменененного юзера из БД как в методе выше
    }

    @Override
    public User get(Integer id) {
        String userRows = "SELECT * FROM USERS WHERE USER_ID = ?";
        return jdbcTemplate.queryForObject(userRows, this::mapRowToUser, id);
    }

    public List<User> findMutual(Integer id, Integer otherId){
        final Set<User> mutualFriendsIds = new HashSet<>(findFriends(id));
        mutualFriendsIds.retainAll(findFriends(otherId));
        return new ArrayList<>(mutualFriendsIds);
    }

    @Override
    public List<User> findFriends(Integer id) {
        String sql1 = "SELECT * " +
                "FROM USERS " +
                "WHERE USER_ID IN " +
                "(SELECT DISTINCT FRIEND_ID " +
                "FROM FRIENDS " +
                "WHERE USER_ID =?)";
        return jdbcTemplate.query(sql1, this::mapRowToUser,id);
    }

    public Map<String, Object> convertUserToDbFormat(User user) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("USER_ID", user.getId());
        parameters.put("NAME", user.getName());
        parameters.put("LOGIN", user.getLogin());
        parameters.put("EMAIL", user.getEmail());
        parameters.put("BIRTHDAY", user.getBirthday());
        return parameters;
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getInt("USER_ID"),
                resultSet.getString("EMAIL"),
                resultSet.getString("LOGIN"),
                resultSet.getString("NAME"),
                resultSet.getDate("BIRTHDAY").toLocalDate(),
                friendStorage.getFriendsOfUser(resultSet.getInt("USER_ID")));
    }

    @Override
    public void checkUserId(Integer id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT * " +
                "FROM USERS U " +
                "WHERE USER_ID = ?", id);
        if(!userRows.next()) {throw new UnknownIdException("Указан некорректный Id пользователя");}
    }

    @Override
    public boolean isLoginAlreadyExist(User user){
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT * " +
                        "FROM USERS U " +
                        "WHERE NAME = ?",
                user.getName());
        return !filmRows.next();
    }

    @Override
    public boolean isEmailAlreadyExist(User user){
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT * " +
                        "FROM USERS U " +
                        "WHERE EMAIL = ?",
                user.getEmail());
        return !filmRows.next();
    }
}
