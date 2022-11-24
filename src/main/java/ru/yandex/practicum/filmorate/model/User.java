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

    @Email
    @NotEmpty
    @EqualsAndHashCode.Include
    private String email;

    @NotEmpty
    private String login;

    private String name;

    @Past
    private LocalDate birthday;

    @JsonIgnore
    private Set<Integer> friends = new HashSet<>();

    public void addFriend(Integer userId){
        friends.add(userId);
    }

    public void deleteFriend(Integer userId){
        friends.remove(userId);
    }
}
