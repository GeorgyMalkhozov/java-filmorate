package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Genre {

    private Integer id;

    @NotBlank(message = "Название жанра не должно быть пустым")
    @JsonIgnore
    private String name;

}
