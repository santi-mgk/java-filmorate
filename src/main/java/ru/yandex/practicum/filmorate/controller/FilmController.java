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
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private static final LocalDate BORDER_DATE_FOR_FILMS = LocalDate.of(1895, 12, 28);
    private int id = 0;
    private final Map<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {

        validate(film);

        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Film " + film.getName() + " created.");

        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {

        validate(film);

        if (!films.containsKey(film.getId())) {
            log.debug(film.getName() + ": film not found.");
            throw new ValidateException(film.getName() + ": film not found.");
        }

        films.put(film.getId(), film);
        log.info("Film " + film.getName() + " updated.");

        return film;
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(BORDER_DATE_FOR_FILMS)) {
            log.debug(film.getName() + ": Release date < 28.12.1895.");
            throw new ValidateException(film.getName() + ": Release date < 28.12.1895.");
        }
    }

    @GetMapping
    public Collection<Film> findAllFilms() {
        return films.values();
    }

    private int getNextId() {
        return ++id;
    }
}
