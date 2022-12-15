package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
public class User {

    private int id;

    @NotBlank(message = "Отсутствует email")
    @Email(message = "Некорректный email.")
    private String email;

    @NotBlank(message = "Отсутствует логин.")
    private String login;

    private String name;

    @NotNull(message = "не указана дата рождения.")
    @Past(message = "Некорректная дата рождения.")
    private LocalDate birthday;

}
