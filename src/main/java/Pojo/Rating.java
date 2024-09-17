package Pojo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    private String Site;

    private double UserRating;

    private double UserMaxRating;

    private double ExpertRating;

    private double ExpertMaxRating;

}
