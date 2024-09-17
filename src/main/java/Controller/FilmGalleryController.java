package Controller;

import Pojo.Film;
import Pojo.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FilmGalleryController {

    @Autowired
    private final MongoTemplate mongoTemplate;

    public FilmGalleryController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}
