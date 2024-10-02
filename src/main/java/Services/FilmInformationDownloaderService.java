package Services;

import FilmAggregation.Aggregator;
import FilmAggregation.FilmwebAggregator;
import FilmAggregation.ImbdAggregator;
import FilmAggregation.RottenTomatoesAggregator;
import Pojos.Rating;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan(basePackages = {"FilmAggregation"})
public class FilmInformationDownloaderService {

    private List<Aggregator> aggregators = new ArrayList<>();

    private String title;

    private String productionYear;

    private String type;

    public void Initialize(String title, String productionYear, String type){
        this.title = title;
        this.productionYear = productionYear;
        this.type = type;
        aggregators.add(new ImbdAggregator(this.title, this.productionYear));
        aggregators.add(new RottenTomatoesAggregator(this.title, this.productionYear, this.type));
        aggregators.add(new FilmwebAggregator(this.title, this.productionYear, this.type));
    }

    public List<Rating> FindFilm(){
        List<Rating> ratings = new ArrayList<>();
        if(!aggregators.isEmpty()) {
            for (Aggregator aggregator : aggregators) {
                ratings.add(aggregator.GetFilmRating());
            }
        }
        return ratings;
    }
}
