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

    @NotBlank
    @EqualsAndHashCode.Include private String name;

    @Size(max = 200)
    private String description;

    @EqualsAndHashCode.Include private LocalDate releaseDate;

    @Positive
    @EqualsAndHashCode.Include private Integer duration;

    @Positive
    private Integer rate;

    @JsonIgnore
    private Set<Integer> likes = new TreeSet<>();

    private Mpa mpa;
    @JsonIgnore
    private Set<Genre> genres = new TreeSet<>(Comparator.comparing(Genre::getId));

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
