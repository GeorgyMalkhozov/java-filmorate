package ru.yandex.practicum.filmorate.storage.friends;

import java.util.Set;

public interface FriendStorage {

    public void create(Integer id ,Integer friendId);

    public void delete(Integer id ,Integer friendId);

    public boolean isFriendshipExists(Integer userId, Integer friendId);

    public Set<Integer> getFriendsOfUser(Integer userId);
}
