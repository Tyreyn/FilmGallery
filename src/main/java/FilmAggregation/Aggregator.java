package FilmAggregation;

import Pojos.Rating;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public abstract class Aggregator {

    private String Url;

    private String SearchXPath;

    private String SearchFormXPath;

    private String UserRatingXPath;

    private String UserRatingMaxXPath;

    private String ExperRatingXPath;

    private String ExpertMaxRatingXPath;

    private String RegexFilmId;

    private String RatingsUrl;

    private String title = null;

    private String productionYear;

    private Rating rating;

    public Aggregator(String title, String productionYear) {
        this.title = title;
        this.productionYear = productionYear;
    }

    public Rating GetFilmRating() {
        this.rating = new Rating();
        this.rating.setSite(Url);
        this.rating.setUrlOrId(FindFilmUrl());
        return rating;
    }

    private String FindFilmUrl() {
        String filmIdOrUrl = null;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.50 Safari/537.36");
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--proxy-server='direct://'");
        options.addArguments("--proxy-bypass-list=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--ignore-certificate-errors");
        WebDriver driver = new ChromeDriver(options);
        try {
            driver = this.GoToFilmSite(driver);
            filmIdOrUrl = ExtractFilmId(driver.getCurrentUrl());
            this.rating = this.GetRating(driver, this.rating);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            driver.quit();
        }
        return filmIdOrUrl;
    }

    protected String ExtractFilmId(String url){
        System.out.println("Searching URL: " + url + " with regex: " + RegexFilmId);
        Pattern pattern = Pattern.compile(RegexFilmId);
        Matcher matcher = pattern.matcher(url);
        matcher.find();
        System.out.println("Matcher find result: " + matcher.group(0));
        return matcher.group(0);
    }

    protected abstract Rating GetRating(WebDriver driver, Rating rating);
    protected abstract WebDriver GoToFilmSite(WebDriver driver) throws IOException;
    protected abstract void Initialize();
}
