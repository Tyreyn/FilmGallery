package FilmAggregation;

public class FilmwebAggregator extends Aggregator {
    private String Url = "";

    private String SearchXPath = "";

    private String SearchFormXPath = "";

    private String RegexFilmId = "";

    private String UserRatingXPath = "";

    private String UserRatingMaxXPath = "";

    private String ExperRatingXPath;

    private String ExpertMaxRatingXPath;

    public FilmwebAggregator(String title, String productionYear) {
        super(title, productionYear);
    }
    
    @Override
    protected void Initialize(){
        setUrl(Url);
        setSearchXPath(SearchXPath);
        setSearchFormXPath(SearchFormXPath);
        setUserRatingXPath(UserRatingXPath);
        setUserRatingMaxXPath(UserRatingMaxXPath);
        setRegexFilmId(RegexFilmId);
        setExperRatingXPath(ExperRatingXPath);
        setExpertMaxRatingXPath(ExpertMaxRatingXPath);
    }
}
