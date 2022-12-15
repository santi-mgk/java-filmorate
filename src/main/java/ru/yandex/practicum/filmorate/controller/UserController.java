package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private int id = 0;
    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {

        validate(user);

        if (user.getId() == 0) {
            user.setId(getNextId());
        }
        users.put(user.getId(), user);
        log.info("User " + user.getName() + " with id= " + user.getId() + " created.");

          return users.get(user.getId());
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {

        validate(user);
        if (!users.containsKey(user.getId())) {
            log.debug(user.getLogin() + ": user not found.");
            throw new ValidateException(user.getLogin() + ": user not found.");
        }

        users.put(user.getId(), user);
        log.info("User " + user.getName() + " with id= " + user.getId() + " updated.");

        return users.get(user.getId());
    }

    private void validate(User user) {

        if (!user.getEmail().contains("@")) {
            log.debug(user.getLogin() + ": email is empty or no symbol '@'.");
            throw new ValidateException(user.getLogin() + ": email is empty or no symbol '@'.");
        }
        if (user.getLogin().contains(" ") || user.getLogin().isEmpty()) {
            log.debug("Login '" + user.getLogin() + "' with email= " + user.getEmail() + ": login is empty or there space.");
            throw new ValidateException("Login '" + user.getLogin() + "' with email= " + user.getEmail() + ": login is empty or there space.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug(user.getLogin() + ": birthday > current date.");
            throw new ValidateException(user.getLogin() + ": birthday > current date.");
        }
        if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    @GetMapping
    public Collection<User> findAllUsers() {
        return users.values();
    }

    private int getNextId() {
        return ++id;
    }


}
