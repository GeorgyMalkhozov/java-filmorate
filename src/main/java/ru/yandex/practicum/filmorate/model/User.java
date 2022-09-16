package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;

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

}
