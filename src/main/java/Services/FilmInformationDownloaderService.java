package Services;

import FilmAggregation.Aggregator;
import FilmAggregation.FilmwebAggregator;
import FilmAggregation.ImbdAggregator;
import FilmAggregation.RottenTomatoesAggregator;
import Pojos.Rating;
import javafx.util.Pair;
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

    public Pair<List<Rating>, String> GetFilmRatings(){
        List<Rating> ratings = new ArrayList<>();
        StringBuilder aggregatorsLogsBuilder = new StringBuilder("Starting aggregation ratings");
        if(!aggregators.isEmpty()) {
            for (Aggregator aggregator : aggregators) {
                ratings.add(aggregator.GetFilmRating(aggregatorsLogsBuilder));
                aggregatorsLogsBuilder.append("\nEnd\n\n");
            }
        }
        return new Pair<>(ratings, aggregatorsLogsBuilder.toString());
    }
}
