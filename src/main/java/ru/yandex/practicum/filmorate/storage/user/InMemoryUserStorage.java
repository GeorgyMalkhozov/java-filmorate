package ru.yandex.practicum.filmorate.storage.user;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UnknownIdException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Data
@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private static int userIdGenerator;
    private Map<Integer, User> users = new HashMap<>();

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        validateUser(user);
        user.setId(generateNewUserId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User put(User user) {
        checkUserId(user.getId());
        validateUser(user);
        users.put(user.getId(),user);
        return user;
    }

    @Override
    public User get(Integer id) {
        checkUserId(id);
        return users.get(id);
    }

    @Override
    public List<User> findFriends(Integer id) {
        checkUserId(id);
        Set<Integer> x1 =  users.get(id).getFriends();
        List<User> result = new ArrayList<>();
        for (Integer integer : x1) {
            result.add(users.get(integer));
        }
        return result;
    }
    private void validateUser(User user) {
        checkLogin(user);
        checkEmail(user);
        if (user.getName() == null || user.getName().isBlank()) {user.setName(user.getLogin());}
    }

    private void checkEmail(User user){
        for (User value : users.values()) {
            if (value.getEmail().equals(user.getEmail()) && (!value.getId().equals(user.getId()))) {
                throw new ValidationException("Пользователь с электронной почтой " +
                        user.getEmail() + " уже зарегистрирован.");
            }
        }
    }

    private void checkLogin(User user){
        if (user.getLogin().contains(" ")){
            throw new ValidationException("Логин пользователя не должен содержать пробелов");
        }
    }

    private int generateNewUserId(){
        userIdGenerator++;
        return userIdGenerator;
    }

    private void checkUserId(Integer id) {
        if (!users.containsKey(id)) {throw new UnknownIdException("Указан некорректный Id пользователя");}
    }
}
