package Services;

import Pojos.Film;
import Pojos.Rating;
import com.mongodb.client.result.UpdateResult;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FilmInformationDownloaderService filmInformationDownloaderService;

    public FilmService(MongoTemplate mongoTemplate)
    {
        this.mongoTemplate = mongoTemplate;
    }

    public Pair<Boolean, String> UpdateFilm(Film updatedFilm, String titleBeforeUpdate){
        UpdateResult updateResult = mongoTemplate.replace(
                new Query(Criteria.where("Title").is(titleBeforeUpdate)),
                updatedFilm);

        return new Pair<>(true, "Successfully saved: " + updatedFilm.getTitles());
    }

    public Pair<Boolean, String> DeleteFilm(Film filmToDelete){

        return new Pair<>(false, "deleting not implemented yet!");
/*
        if(!CheckIfFilmExists(filmToDelete)) {
            return new Pair<>(false, MessageFormat.format("Film {0} not found", filmToDelete));
        }

        mongoTemplate.remove(new Query(Criteria.where("Title").regex(filmToDelete)), Film.class);
        return new Pair<>(true, MessageFormat.format("Film {0} deleted", filmToDelete));
 */
    }

    public Pair<Boolean, String> SaveFilm(Film filmToSave){

        if(CheckIfFilmExistsByNameAndProductionYear(
                filmToSave.getTitles().get(0).getName(),
                filmToSave.getTitles().get(0).getLanguage(),
                filmToSave.getProductionYear())) {
            System.out.println("There is already film with that title");
            return new Pair<>(false, "There is already film with that title");
        }

        this.filmInformationDownloaderService = new FilmInformationDownloaderService();
        this.filmInformationDownloaderService.Initialize(
                filmToSave.getTitles().get(0).getName(),
                filmToSave.getProductionYear(),
                filmToSave.getType());
        var filmRatingsResult = this.filmInformationDownloaderService.GetFilmRatings();
        System.out.println(filmRatingsResult.getValue());
        filmToSave.setRatings(filmRatingsResult.getKey());

        mongoTemplate.save(filmToSave);
        System.out.println(MessageFormat.format(
                "Film {0} saved successfully with Id = {1}",
                filmToSave.getTitles(),
                filmToSave.getId()));
        return new Pair<>(
                true,
                "Successfully saved: " + filmToSave.getTitles() + "\n" + filmRatingsResult.getValue());
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

    private Boolean CheckIfFilmExistsByNameAndProductionYear(
            String filmTitle,
            String filmTitleLanguage,
            String filmProductionYear){
        Query query = new Query();
        query.addCriteria(Criteria.where("Titles").elemMatch(
                Criteria.where("Name")
                        .is(filmTitle)
                        .and("Language")
                        .is(filmTitleLanguage)));
        query.addCriteria(Criteria.where("ProductionYear").is(filmProductionYear));
        return mongoTemplate.exists(query, Film.class);
    }
}
