package ru.yandex.practicum.filmorate.model;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@Data
@EqualsAndHashCode
@Validated
public class Film {

    private Integer id;

    @NotBlank(message = "Название фильма не должно быть пустым")
    @EqualsAndHashCode.Include private String name;

    @Size(max = 200,message = "Описание должно состоять не более чем из 200 символов")
    private String description;

    @EqualsAndHashCode.Include private LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть больше нуля")
    @EqualsAndHashCode.Include private Integer duration;

    private Integer rate;

    @JsonIgnore
    private Set<Integer> likes;

    private Mpa mpa;
    @JsonIgnore
    private Set<Genre> genres;

    public void addLike(Integer userId){
        likes.add(userId);
    }

    public void deleteLike(Integer userId){
        likes.remove(userId);
    }

    public void addGenre(Genre genre){
        genres.add(genre);
    }

    public void deleteGenre(Genre genre){
        genres.remove(genre);
    }
}
