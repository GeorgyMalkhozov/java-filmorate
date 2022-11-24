package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UnknownIdException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class UserService {

    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    @Autowired
    public UserService(UserStorage userStorage, FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    public void addFriend(Integer id ,Integer friendId) {
        checkUsersExist(id, friendId);
        friendStorage.create(id,friendId);
    }

    public void deleteFriend(Integer id, Integer friendId) {
        checkUsersExist(id, friendId);
        if (!friendStorage.isFriendshipExists(id, friendId)){
            throw new UnknownIdException("Пользователи не являются друзьями. Проверьте id.");
        }
        friendStorage.delete(id,friendId);
    }

    public List<User> findMutual(Integer id, Integer otherId) {
        checkUsersExist(id, otherId);
        return userStorage.findMutual(id, otherId);
    }

    public List<User> findAll() {
        return userStorage.findAll();
    };

    public User create(User user) {
        validateUser(user);
        return userStorage.create(user);
    };

    public User put(User user) {
        userStorage.checkUserId(user.getId());
        validateUser(user);
        return userStorage.put(user);
    };

    public User get(Integer id) {
        userStorage.checkUserId(id);
        return userStorage.get(id);
    };

    public List<User> findFriends(Integer id) {
        return userStorage.findFriends(id);
    };

    private void checkUsersExist(Integer user1, Integer user2) {
        userStorage.checkUserId(user1);
        userStorage.checkUserId(user2);
    }

    private void validateUser(User user) {
        checkLogin(user);
    //    checkEmail(user);
        if (user.getName() == null || user.getName().isBlank()) {user.setName(user.getLogin());}
    }

/*    private void checkEmail(User user){
        for (User value : users.values()) {
            if (value.getEmail().equals(user.getEmail()) && (!value.getId().equals(user.getId()))) {
                throw new ValidationException("Пользователь с электронной почтой " +
                        user.getEmail() + " уже зарегистрирован.");
            }
        }
    }*/

    private void checkLogin(User user){
        if (user.getLogin().contains(" ")){
            throw new ValidationException("Логин пользователя не должен содержать пробелов");
        }
    }
}
