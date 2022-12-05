package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode
@Validated
@AllArgsConstructor
public class Mpa {

    private Integer id;

    @NotBlank(message = "Название Mpa не должно быть пустым")
    @JsonIgnore
    private String name;
}
