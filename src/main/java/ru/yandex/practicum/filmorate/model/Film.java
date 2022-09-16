package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Film {

    @EqualsAndHashCode.Exclude private Integer id;

    @NotBlank
    private String name;

    @Size(max = 200)
    @EqualsAndHashCode.Exclude private String description;
    private LocalDate releaseDate;

    @Positive
    private Integer duration;

}
