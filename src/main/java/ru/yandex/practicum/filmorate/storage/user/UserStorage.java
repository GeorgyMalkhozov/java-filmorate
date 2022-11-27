package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    public List<User> findAll();

    public User create(User user);

    public User put(User user);

    public User get(Integer id);

    public List<User> findFriends(Integer id);

    public List<User> findMutual(Integer id, Integer otherId);

    public void checkUserId(Integer id);

    public boolean isLoginAlreadyExist(User user);

    public boolean isEmailAlreadyExist(User user);
}
