package FilmAggregation;

import Pojos.Rating;
import javafx.util.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FilmwebAggregator extends Aggregator {
    private String Url = "https://www.filmweb.pl";

    private String ResultTitleXPath = "//*[@id=\"site\"]/div[3]/div[2]/div/div[1]/div[2]/div[%d]/div[2]/span";

    private String ResultXPath = "//*[@id=\"site\"]/div[3]/div[2]/div/div[1]/div[2]/div[%d]/div[2]/div[1]/div[1]/div[1]/a";

    private String RegexFilmId = "(?<=\\/)[A-Za-z0-9\\-\\+]+$";

    private String ExperRatingXPath = "//*[@id=\"site\"]/div[3]/div[2]/div/div[1]/section/div/div[2]/div/div/div[2]/div[4]/div/div[1]/div[1]/span[1]";

    private String UserRatingXPath = "//*[@id=\"site\"]/div[3]/div[2]/div/div[1]/section/div/div[2]/div/div/div[2]/div[4]/div/div[2]/div/span[1]";

    private String RatingsUrl = "";

    private String title;

    private String type;

    private String productionYear;

    public FilmwebAggregator(String title, String productionYear, String type) {
        super(title, productionYear);
        this.title = title;
        this.productionYear = productionYear;
        this.type = type;
        Initialize();
    }

    @Override
    protected WebDriver GoToFilmSite(WebDriver driver){
        try {
            driver.get(this.Url+ String.format("/search#/?query=%s",this.title + " " + this.productionYear));
            Thread.sleep(5000);

            for(int i = 1; i < 5; i++) {
                WebElement filmTitle = driver.findElement(By.xpath(String.format(ResultTitleXPath, 1)));
                String filmLink = filmTitle.getText();
                if(filmLink.toLowerCase().equals(this.type.toLowerCase())) {
                    WebElement filmUrl = driver.findElement(By.xpath(String.format(ResultXPath, 1)));
                    String url = filmUrl.getAttribute("href");
                    System.out.println("Film: " + " -> " + url);
                    driver.get(url);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }

    @Override
    protected Rating GetRating(WebDriver driver, Rating rating){
        WebElement webElement = driver.findElement(By.xpath(UserRatingXPath));
        Pair<Double, Double> ratingsPair = ParseRatings(webElement);
        rating.setUserRating(ratingsPair.getKey());
        rating.setUserMaxRating(ratingsPair.getValue());

        webElement = driver.findElement(By.xpath(ExperRatingXPath));
        ratingsPair = ParseRatings(webElement);
        rating.setExpertRating(ratingsPair.getKey());
        rating.setExpertMaxRating(ratingsPair.getValue());
        return rating;
    }

    @Override
    protected void Initialize(){
        setUrl(Url);
        setUserRatingXPath(UserRatingXPath);
        setRegexFilmId(RegexFilmId);
        setExperRatingXPath(ExperRatingXPath);
        setRatingsUrl(RatingsUrl);
    }

    private Pair<Double, Double> ParseRatings(WebElement webElement){
        String ratingNumber = webElement.getText().replaceAll(",",".");
        String ratingMaxNumber = "10";
        System.out.println("rating: " + ratingNumber + "/" + ratingMaxNumber);
        return new Pair<>(Double.parseDouble(ratingNumber), Double.parseDouble(ratingMaxNumber));
    }
}
