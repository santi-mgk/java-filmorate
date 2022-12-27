package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {
    FilmController filmController;
    Film film;

    @BeforeEach
    void getController() {
        filmController = new FilmController();
    }

    @Test
    void blankFilmNameTest() {
        film = Film.builder()
                .name("")
                .id(1)
                .description("test description")
                .releaseDate(LocalDate.now().minusDays(30))
                .duration(100)
                .build();

        filmController.createFilm(film);

        assertEquals(filmController.findAllFilms().size(), 1);
    }

    @Test
    void longFilmDescriptionTest() {
        String longDescription = fillString(201, "r");
        film = Film.builder()
                .name("Test")
                .id(1)
                .description(longDescription)
                .releaseDate(LocalDate.now().minusDays(30))
                .duration(100)
                .build();

        filmController.createFilm(film);

        assertEquals(filmController.findAllFilms().size(), 1);
    }

    @Test
    void releaseDateFilmTest() {
        film = Film.builder()
                .name("Test")
                .id(1)
                .description("Test description")
                .releaseDate(LocalDate.now().minusYears(150))
                .duration(100)
                .build();

        final ValidateException ex = assertThrows(
                ValidateException.class,
                () -> filmController.createFilm(film)
        );

        assertEquals(ex.getMessage(), "Test: Release date < 28.12.1895.");
    }

    @Test
    void negativeDurationFilmTest() {
        film = Film.builder()
                .name("Test")
                .id(1)
                .description("Test description")
                .releaseDate(LocalDate.now().minusYears(1))
                .duration(-1)
                .build();

        filmController.createFilm(film);

        assertEquals(filmController.findAllFilms().size(), 1);
    }

    private String fillString(int count,String c) {
        return c.repeat(count);
    }
}
