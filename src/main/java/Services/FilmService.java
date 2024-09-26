package Services;

import Pojos.Film;
import Pojos.Rating;
import com.mongodb.client.result.UpdateResult;
import javafx.util.Pair;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class FilmService {

    private final MongoTemplate mongoTemplate;

    public FilmService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Pair<Boolean, String> UpdateFilm(Film updatedFilm, String titleBeforeUpdate){
        UpdateResult updateResult = mongoTemplate.replace(
                new Query(Criteria.where("Title").is(titleBeforeUpdate)),
                updatedFilm);

        return new Pair<>(true, "Successfully saved: " + updatedFilm.getTitles());
    }

    public List<Film> GetFilmsStartingWith(String filmTitle) {
        return mongoTemplate.find(
                new Query(Criteria.where("Title").regex("^"+filmTitle)), Film.class);
    }

    public Pair<Boolean, String> DeleteFilm(String filmToDelete){

        if(!getFilmByFullName(filmToDelete)) {
            return new Pair<>(false, MessageFormat.format("Film {0} not found", filmToDelete));
        }

        mongoTemplate.remove(new Query(Criteria.where("Title").regex(filmToDelete)), Film.class);
        return new Pair<>(true, MessageFormat.format("Film {0} deleted", filmToDelete));
    }

    public Pair<Boolean, String> SaveFilm(Film filmToSave){

        if(getFilmByFullName(filmToSave.getTitles().get(0).getName())) {
            System.out.println("There is already film with that title");
            return new Pair<>(false, "There is already film with that title");
        }

        mongoTemplate.save(filmToSave);
        System.out.println(MessageFormat.format(
                "Film {0} saved successfully with Id = {1}",
                filmToSave.getTitles(),
                filmToSave.getId()));
        return new Pair<>(true, "Successfully saved: " + filmToSave.getTitles());
    }

    public Pair<Boolean, String> Update(String key, String value){
        Query searchQuery = new Query(Criteria.where(key).is(value));
        mongoTemplate.updateFirst(
                searchQuery,
                Update.update(key, value),
                value.equals("Ratings") ? Rating.class : Film.class );
        return new Pair<>(true, "Successfully updated: " + value);
    }

    public List<Film> ListAll(){
        return mongoTemplate.findAll(Film.class);
    }

    private Boolean getFilmByFullName(String filmTitle){
        return mongoTemplate.exists(
                new Query(Criteria.where("Title").is(filmTitle)), Film.class);
    }
}
