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

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private int id = 0;
    private final Map<Integer, Film> films = new HashMap<>();
    private static final LocalDate BORDER_DATE_FOR_FILMS = LocalDate.of(1895, 12, 28);

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        if (film.getName().isEmpty() || film.getName().isBlank() || film.getName() == null) {
            log.error("Name film is empty.");
            throw new ValidateException("Name film is empty.");
        } else if (film.getDescription().length() > 200) {
            log.error("Film " + film.getName() + " not created. Description film >200 symbols");
            throw new ValidateException("Film " + film.getName() + " not created. Description film >200 symbols");
        } else if (film.getReleaseDate().isBefore(BORDER_DATE_FOR_FILMS)) {
            log.error("Film " + film.getName() + " not created. Release date < 28.12.1895.");
            throw new ValidateException("Film " + film.getName() + " not created. Release date < 28.12.1895.");
        } else if (film.getDuration() <= 0) {
            log.error("Film " + film.getName() + " not created. Duration must be > 0.");
            throw new ValidateException("Film " + film.getName() + " not created. Duration must be > 0.");
        }
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Film " + film.getName() + " created.");

        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            log.error(film.getName() + " with id = " + film.getId() + " not found.");
            throw new ValidateException(film.getName() + " with id = " + film.getId() + " not found.");
        }
        if (film.getDescription().length() > 200) {
            log.error("Film " + film.getName() + " not updated. Description film >200 symbols");
            throw new ValidateException("Film " + film.getName() + " not updated. Description film >200 symbols");
        } else if (film.getReleaseDate().isBefore(BORDER_DATE_FOR_FILMS)) {
            log.error("Film " + film.getName() + " not updated. Release date < 28.12.1895.");
            throw new ValidateException("Film " + film.getName() + " not updated. Release date < 28.12.1895.");
        } else if (film.getDuration() <= 0) {
            log.error("Film " + film.getName() + " not updated. Duration must be > 0.");
            throw new ValidateException("Film " + film.getName() + " not updated. Duration must be > 0.");
        }
        films.put(film.getId(), film);
        log.info("Film " + film.getName() + " updated.");

        return film;
    }

    @GetMapping
    public Collection<Film> findAllFilms() {
        return films.values();
    }

    public int getNextId() {
        return ++id;
    }
}
