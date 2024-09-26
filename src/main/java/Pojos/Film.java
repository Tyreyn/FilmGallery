package Pojos;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "films")
public class Film {

    @Id
    private ObjectId Id;
    private List<Title> Titles;
    private String Description;
    private String ProductionYear;
    private List<Rating> Ratings;
    private List<String> Genres;
    private String Type;
    private List<String> UrlPaths;

    public Film(
            List<Title> Titles,
            String Description,
            String ProductionYear,
            List<Rating> Ratings,
            List<String> Genres,
            String Type,
            List<String> UrlPath)
    {
        this.Titles = Titles;
        this.Description = Description;
        this.ProductionYear = ProductionYear;
        this.Ratings = Ratings;
        this.Genres = Genres;
        this.Type = Type;
        this.UrlPaths = UrlPath;
    }

    public Film(
            List<Title>  Titles,
            String Description,
            String ProductionYear,
            List<String> Genres,
            String Type)
    {
        this.Titles = Titles;
        this.Description = Description;
        this.ProductionYear = ProductionYear;
        this.Genres = Genres;
        this.Type = Type;
    }

}
