package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Builder
public class User {

    private int id;

    @NotNull(message = "Email not found")
    @Email(message = "Email invalid.")
    private String email;

    @NotBlank(message = "Login not found.")
    private String login;

    private String name;

    @NotNull(message = "Birthday not found.")
    @Past(message = "Birthday invalid.")
    private LocalDate birthday;

}
