package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.time.LocalDate;

public class UserControllerTest {
    UserController userController;
    User user;

    @BeforeEach
    void getController() {
        userController = new UserController();
    }

    @Test
    void blankEmailValidationTest() {
        user = User.builder()
                .id(1)
                .email("")
                .login("Test")
                .name("Name")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        userController.createUser(user);

        assertEquals(userController.findAllUsers().size(), 0);
    }

    @Test
    void emailWithoutAtSignValidationTest() {
        user = User.builder()
                .id(1)
                .email("test.yandex.ru")
                .login("Test")
                .name("Name")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        userController.createUser(user);

        assertEquals(userController.findAllUsers().size(), 0);
    }

    @Test
    void blankLoginValidationTest() {
        user = User.builder()
                .id(1)
                .email("test@yandex.ru")
                .login("")
                .name("Name")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        userController.createUser(user);

        assertEquals(userController.findAllUsers().size(), 0);
    }

    @Test
    void loginWithSpacesValidationTest() {
        user = User.builder()
                .id(1)
                .email("test@yandex.ru")
                .login("User Test")
                .name("Name")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        final ValidateException ex = assertThrows(
                ValidateException.class,
                () -> userController.createUser(user)
        );

        assertEquals(ex.getMessage(), "Login 'User Test' with email= test@yandex.ru: login is empty or there space.");
    }

    @Test
    void birthdayInFutureValidationTest() {
        user = User.builder()
                .id(1)
                .email("test@yandex.ru")
                .login("User")
                .name("Name")
                .birthday(LocalDate.now().plusYears(1))
                .build();

        userController.createUser(user);

        assertEquals(userController.findAllUsers().size(), 0);
    }

    @Test
    void blankNameValidationTest() {
        user = User.builder()
                .id(1)
                .email("test@yandex.ru")
                .login("User")
                .name("")
                .birthday(LocalDate.now().minusYears(12))
                .build();

        userController.createUser(user);

        assertEquals(user.getName(), user.getLogin());
    }
}
