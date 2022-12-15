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

    @NotBlank(message = "Неправильное название фильма.")
    private String name;

    @NotNull(message = "Отсутствует описание фильма.")
    @Size(max = 200, message = "слишком длинное описание, больше 200 символов.")
    private String description;

    private LocalDate releaseDate;

    @Min(value = 1, message = "Неправильная продолжительность фильма.")
    @Positive
    private int duration;

}
