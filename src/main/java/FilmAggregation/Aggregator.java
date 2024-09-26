package FilmAggregation;

import Pojos.Rating;
import lombok.Getter;
import lombok.Setter;
import org.htmlunit.WebClient;
import org.htmlunit.html.*;

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

    public String FindFilmUrl() {
        HtmlPage page = null;
        try (final WebClient webClient = new WebClient()) {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            page = webClient.getPage(this.Url);
            HtmlInput messageText = page.getFirstByXPath(SearchXPath);
            messageText.type(this.title + " " + this.productionYear);
            HtmlForm htmlForm = page.getFormByName(SearchFormXPath);
            for (DomElement childElement : htmlForm.getChildElements()) {
                if (childElement.getNodeName().equals("button") &&
                        childElement.getAttribute("type").equals("submit")) {
                    page = childElement.click();
                }
            }

            HtmlElement htmlElement = page.getFirstByXPath(UserRatingXPath);
            HtmlPage filmPage = htmlElement.click();
            page = webClient.getPage(String.format(
                    "https://www.imdb.com/title/%s/ratings/",
                    ExtractFilmId(filmPage.getUrl().toString())));
            htmlElement = page.getFirstByXPath(UserRatingMaxXPath);

            String ratingNumber = htmlElement
                    .getFirstChild()
                    .getFirstChild()
                    .getVisibleText()
                    .replaceAll("^[a-zA-Z\\/]", "");
            String ratingMaxNumber = htmlElement
                    .getFirstChild()
                    .getNextSibling()
                    .getVisibleText()
                    .replaceAll("^[a-zA-Z\\/]", "");
            this.rating.setExpertRating(Double.parseDouble(ratingNumber));
            this.rating.setUserMaxRating(Double.parseDouble(ratingMaxNumber));
            System.out.println("rating: " + ratingNumber + "/" + ratingMaxNumber);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String ExtractFilmId(String url){
        System.out.println("Testing URL: " + url);
        Pattern pattern = Pattern.compile(RegexFilmId);
        Matcher matcher = pattern.matcher(url);
        System.out.println("Matcher find result: " + matcher.find());
        return matcher.group(0);
    }

    protected abstract void Initialize();
}
