package ru.yandex.practicum.filmorate.model;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    private Integer id;

    @Email(message = "Некорректный формат email")
    @NotEmpty(message = "Email должен быть заполнен")
    @EqualsAndHashCode.Include
    private String email;

    @NotEmpty(message = "Логин не должен быть пустым")
    private String login;

    private String name;

    @Past(message = "Дата рождения пользователя должна быть в прошлом")
    private LocalDate birthday;

    @JsonIgnore
    private Set<Integer> friends;

    public void addFriend(Integer userId){
        friends.add(userId);
    }

    public void deleteFriend(Integer userId){
        friends.remove(userId);
    }
}
