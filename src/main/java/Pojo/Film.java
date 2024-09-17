package Pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "films")
public class Film {

    @Id
    private ObjectId Id;
    private String Title;
    private String Description;
    private String ProductionYear;
    private List<Rating> Ratings;
    private List<String> Genre;
    private String Type;
    private List<String> UrlPath;

    public Film(
            String Title,
            String Description,
            String ProductionYear,
            List<Rating> Ratings,
            List<String> Genre,
            String Type,
            List<String> UrlPath)
    {
        this.Title = Title;
        this.Description = Description;
        this.ProductionYear = ProductionYear;
        this.Ratings = Ratings;
        this.Genre = Genre;
        this.Type = Type;
        this.UrlPath = UrlPath;
    }

}
