package FilmAggregation;

public class ImbdAggregator extends Aggregator {

    private String Url = "https://www.imdb.com/";

    private String SearchXPath = "//*[@id=\"suggestion-search\"]";

    private String SearchFormXPath = "nav-search-form";

    private String RegexFilmId = "tt\\d+";

    private String UserRatingXPath = "//*[@id=\"__next\"]/main/div[2]/div[2]/section/div/div[1]/" +
            "section[2]/div[2]/ul/li[1]/div[2]/div/a";

    private String UserRatingMaxXPath = "//*[@id=\"__next\"]/main/div/section/div/section/div/div[1]/" +
            "section[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[1]";

    private String ExperRatingXPath;

    private String ExpertMaxRatingXPath;

    public ImbdAggregator(String title, String productionYear) {
        super(title, productionYear);
        Initialize();
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
