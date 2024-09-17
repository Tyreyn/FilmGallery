package com.example.backend;

import Pojo.Film;
import Pojo.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Initializer implements CommandLineRunner {

    @Autowired
    private final MongoTemplate mongoTemplate;

    public Initializer(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) {
        Rating rating = new Rating(
                "www.filmweb.pl",
                7.7,
                10,
                7.2,
                10);
        Film film1 = new Film(
                "Uznany za niewinnego",
                "Szokujące zabójstwo stawia na nogi prokuraturę w Chicago, gdy podejrzana" +
                        " staje się jedna z pracujących tam osób, którą następnie czeka walka o rodzinę.",
                "2024",
                List.of(rating),
                List.of("Kryminał"),
                "Film",
                List.of("https://fwcdn.pl/fpo/78/70/10007870/8131615.10.webp", "https://fwcdn.pl/fpo/78/70/10007870/8128950.10.webp"));
        mongoTemplate.save(film1);
        System.out.println(film1);
    }
}
