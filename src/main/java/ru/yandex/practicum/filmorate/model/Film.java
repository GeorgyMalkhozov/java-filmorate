package ru.yandex.practicum.filmorate.model;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @JsonIgnore
    private final Set<Integer> likes = new HashSet<>();

    public void addLike(Integer userId){
        likes.add(userId);
    }

    public void deleteLike(Integer userId){
        likes.remove(userId);
    }
}
