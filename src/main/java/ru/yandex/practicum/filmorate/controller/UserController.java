package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Qualifier("inMemoryUserStorage")
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {this.userService = userService;}

    @GetMapping("/users")
    @ResponseBody
    public List<User> findAll() {
        log.debug("Получен запрос GET/users");
        return userService.findAll();
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) {
        log.debug("Получен запрос POST/user");
        return userService.create(user);
    }

    @PutMapping("/users")
    public User put(@Valid @RequestBody User user) {
        log.debug("Получен запрос PUT/user");
        return userService.put(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.debug("Получен запрос на добавление лайка");
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.debug("Получен запрос на добавление лайка");
        userService.deleteFriend(id,friendId);
    }

    @GetMapping("/users/{id}")
    public User get(@PathVariable Integer id) {
        log.debug("Получен запрос GET users/user.");
        return userService.get(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.debug("Получен запрос GET /users/{id}/friends/common/{otherId}");
        return userService.findMutual(id, otherId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) {
        log.debug("Получен запрос GET /users/{id}/friends");
        return userService.findFriends(id);
    }
}
