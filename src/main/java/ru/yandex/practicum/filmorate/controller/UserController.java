package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.UnknownIdException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private static int userIdGenerator;
    private Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public List<User> findAll() {
        log.debug("Получен запрос GET /users.");
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.debug("Получен запрос POST /user.");
        validateUser(user);
        user.setId(generateNewUserId());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        log.debug("Получен запрос PUT /user.");
        if (!users.containsKey(user.getId())) {throw new UnknownIdException("Указан некорректный Id пользователя");}
        validateUser(user);
        users.put(user.getId(),user);
        return user;
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
}
