package FilmAggregation;

import Pojos.Rating;

import java.io.File;
import java.io.IOException;

import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

public class ImbdAggregator extends Aggregator {

    private String Url = "https://www.imdb.com/";

    private String SearchXPath = "//*[@id=\"suggestion-search\"]";

    private String SearchFormXPath = "//*[@id=\"suggestion-search-button\"]";

    private String RegexFilmId = "tt\\d+";

    private String FirstResultXPath = "//*[@id=\"__next\"]/main/div[2]/div[3]/section/div/" +
            "div[1]/section[2]/div[2]/ul/li[1]";

    private String UserRatingButtonXPath = "//*[@id=\"__next\"]/main/div/section[1]/section/" +
            "div[3]/section/section/div[3]/div[2]/div[2]/div[1]/div/div[1]/a";

    private String UserRatingXPath = "//*[@id=\"__next\"]/main/div/section[1]/section/div[3]/" +
            "section/section/div[3]/div[2]/div[2]/div[1]/div/div[1]/a/span/div/div[2]/div[1]/span[1]";

    private String RatingsUrl = "https://www.imdb.com/title/%s/ratings/";

    private String title;

    private String productionYear;

    public ImbdAggregator(String title, String productionYear) {
        super(title, productionYear);
        this.title = title;
        this.productionYear = productionYear;
        Initialize();
    }

    @Override
    protected WebDriver GoToFilmSite(WebDriver driver){
        try {
            driver.get(this.Url);
            Thread.sleep(5000);
//            TakesScreenshot screen = (TakesScreenshot) driver;
//            File srcFile = screen.getScreenshotAs(OutputType.FILE);
//            File destFile = new File("C:\\Users\\athos\\source\\repos\\FilmGallery\\google.png");
//            FileUtils.copyFile(srcFile, destFile);
            driver.findElement(By.xpath(SearchXPath)).sendKeys(this.title + " " + this.productionYear);
            driver.findElement(By.xpath(SearchFormXPath)).click();
            driver.findElement(By.xpath(FirstResultXPath)).click();
            System.out.println("Film url: " + driver.getCurrentUrl());
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
        rating.setExpertRating(0);
        rating.setExpertMaxRating(0);
        return rating;
    }

    @Override
    protected void Initialize(){
        setUrl(Url);
        setSearchXPath(SearchXPath);
        setSearchFormXPath(SearchFormXPath);
        setUserRatingXPath(UserRatingXPath);
        setRegexFilmId(RegexFilmId);
        setRatingsUrl(RatingsUrl);
    }

    private Pair<Double, Double> ParseRatings(WebElement webElement){
        String ratingNumber = webElement.getAttribute("innerHTML");
        System.out.println("rating: " + ratingNumber + "/10");
        return new Pair<>(Double.parseDouble(ratingNumber), Double.parseDouble("10"));
    }
}
