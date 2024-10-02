package FilmAggregation;

import Pojos.Rating;
import javafx.util.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

public class RottenTomatoesAggregator extends Aggregator {

    private String Url = "https://www.rottentomatoes.com";

    private String SearchXPath = "https://www.rottentomatoes.com/search/?search=%s";

    private String ResultListTitleXPath = "//*[@id=\"search-results\"]/search-page-result[1]/h2";

    private String ResultListXPath = "//*[@id=\"search-results\"]/search-page-result[%d]/ul";

    private String RegexFilmId = "[a-zA-Z_]+$";

    private String UserRatingXPath = "//*[@id=\"modules-wrap\"]/div[1]/media-scorecard/rt-button[5]/rt-text";

    private String ExperRatingXPath = "//*[@id=\"modules-wrap\"]/div[1]/media-scorecard/rt-button[2]/rt-text";

    private String RatingsUrl = "";

    private String title;

    private String type;

    private String productionYear;

    public RottenTomatoesAggregator(String title, String productionYear, String type) {
        super(title, productionYear);
        this.title = title;
        this.productionYear = productionYear;
        this.type = type;
        Initialize();
    }

    @Override
    protected WebDriver GoToFilmSite(WebDriver driver){
        try {
            String searchString = this.title + " " + this.productionYear;
            driver.get(String.format(
                    SearchXPath,
                    searchString.replaceAll(" ", "%20")));


            String siteFilmType = driver
                    .findElement(By.xpath(ResultListTitleXPath))
                    .getText();
            WebElement resultList;
            if((this.type.equals("Film") && siteFilmType.equals("MOVIES")) ||
                this.type.equals("Serial") && siteFilmType.equals("TV shows")){
                resultList = driver.findElement(By.xpath(String.format(
                        ResultListXPath,
                        1)));
            }else {
                resultList = driver.findElement(By.xpath(String.format(
                        ResultListXPath,
                        2)));
            }
            driver.get(resultList
                    .findElement(By.tagName("search-page-media-row"))
                    .findElement(By.cssSelector("[slot=\"title\"]"))
                    .getAttribute("href"));
            System.out.println("Film url: " + driver.getCurrentUrl().toString());
            return driver;
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
        System.out.println("Starting parsing: " + webElement.getText());
        NumberFormat percentageFormat = NumberFormat.getPercentInstance();
        double ratingNumber, ratingMaxNumber;
        try {
            ratingNumber = percentageFormat
                    .parse(webElement.getText())
                    .doubleValue() * 10;
            ratingMaxNumber = 10;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println("rating: " + round(ratingNumber, 2) + "/" + ratingMaxNumber);
        return new Pair<>(round(ratingNumber, 2), ratingMaxNumber);
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
