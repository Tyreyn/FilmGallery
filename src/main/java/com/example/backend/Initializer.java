package com.example.backend;

import Pojos.Film;
import Pojos.Rating;
import Pojos.Title;
import Services.FilmService;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Initializer {

    public Initializer(@NotNull FilmService filmService) {
        Rating rating = new Rating(
                "www.filmweb.pl",
                "tt17677860",
                7.7,
                10,
                7.2,
                10);
        Film film1 = new Film(
                List.of(
                        new Title("Presumed Innocent","ENG"),
                        new Title("Uznany za niewinnego", "PL")),
                "Szokujące zabójstwo stawia na nogi prokuraturę w Chicago, gdy podejrzana" +
                        " staje się jedna z pracujących tam osób, którą następnie czeka walka o rodzinę.",
                "2024",
                List.of(rating),
                List.of("Kryminał"),
                "Film",
                List.of("https://fwcdn.pl/fpo/78/70/10007870/8131615.10.webp", "https://fwcdn.pl/fpo/78/70/10007870/8128950.10.webp"));
        filmService.SaveFilm(film1);
    }
}
