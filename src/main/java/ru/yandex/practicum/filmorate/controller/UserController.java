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
    public User createUser(@RequestBody User user) {
        if ((!user.getEmail().contains("@")) || (user.getEmail().isBlank()) || (user.getEmail().isEmpty())) {
            log.error("User" + user.getLogin() + " not created. Email is empty or no symbol '@'.");
            throw new ValidateException("User" + user.getLogin() + " not created. Email is empty or no symbol '@'.");
        }  else if ((user.getLogin().contains(" ")) || (user.getLogin().isEmpty()) || (user.getLogin().isBlank())) {
            log.error("User " + user.getLogin() + " with email= " + user.getEmail() + " not created. " +
                    "Login is empty or there space.");
            throw new ValidateException("User " + user.getLogin() + " with email= " + user.getEmail() + " not created. " +
                    "Login is empty or there space.");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("User" + user.getLogin() + " not created. Birthday > current date.");
            throw new ValidateException("User" + user.getLogin() + " not created. Birthday > current date.");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }

        if (user.getId() == 0) {
            user.setId(getnextId());
        }
        users.put(user.getId(), user);
        log.info("User " + user.getName() + " with id= " + user.getId() + " created.");

          return users.get(user.getId());
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            log.error(user.getName() + " with id = " + user.getId() + " not found.");
            throw new ValidateException(user.getName() + " with id = " + user.getId() + " not found.");
        }
        if (!user.getEmail().contains("@")) {
            log.error("User" + user.getLogin() + " not updated. Email is empty or no symbol '@'.");
            throw new ValidateException("User" + user.getLogin() + " not updated. Email is empty or no symbol '@'.");
        } else if (user.getLogin().contains(" ")) {
            log.error("User " + user.getLogin() + " with email= " + user.getEmail() + " not updated. " +
                    "Login is empty or there space.");
            throw new ValidateException("User " + user.getLogin() + " with email= " + user.getEmail() + " not updated. " +
                    "Login is empty or there space.");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("User" + user.getLogin() + " not updated. Birthday > current date.");
            throw new ValidateException("User" + user.getLogin() + " not updated. Birthday > current date.");
        }
        if (user.getName().equals("")) {
            user.setName(user.getLogin());
        }

        users.put(user.getId(), user);
        log.info("User " + user.getName() + " with id= " + user.getId() + " updated.");

        return users.get(user.getId());
    }

    @GetMapping
    public Collection<User> findAllUsers() {
        return users.values();
    }

    public int getnextId() {
        return ++id;
    }
}
