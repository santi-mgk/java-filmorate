package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
public class Film {

    private int id;

    @NotBlank(message = "Invalid name film.")
    private String name;

    @NotNull(message = "Not found description film.")
    @Size(max = 200, message = "description film must be < 200 characters.")
    private String description;

    private LocalDate releaseDate;

    @Positive
    private int duration;

}
