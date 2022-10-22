package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UnknownIdException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Qualifier("inMemoryUserStorage")
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Integer id ,Integer friendId) {
        checkUsersExist(id, friendId);
        userStorage.get(id).addFriend(friendId);
        userStorage.get(friendId).addFriend(id);
    }

    public void deleteFriend(Integer id, Integer friendId) {
        checkUsersExist(id, friendId);
       userStorage.get(id).deleteFriend(friendId);
       userStorage.get(friendId).deleteFriend(id);
    }

    public List<User> findMutual(Integer id, Integer otherId) {
        checkUsersExist(id, otherId);
        final Set<Integer> mutualFriendsIds = new HashSet<>(userStorage.get(id).getFriends());
        mutualFriendsIds.retainAll(userStorage.get(otherId).getFriends());
        return mutualFriendsIds.stream()
                .map(userStorage::get)
                .collect(Collectors.toList());
    }

    public List<User> findAll() {
        return userStorage.findAll();
    };

    public User create(User user) {
        return userStorage.create(user);
    };

    public User put(User user) {
        return userStorage.put(user);
    };

    public User get(Integer id) {
        return userStorage.get(id);
    };

    public List<User> findFriends(Integer id) {
        return userStorage.findFriends(id);
    };

    private void checkUsersExist(Integer user1, Integer user2) {
        if (userStorage.get(user1)==null || userStorage.get(user2)==null) {
            throw new UnknownIdException("Указан некорректный Id пользователя");
        }
    }
}
