package Pojos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rating {

    private String Site;

    private String UrlOrId;

    private double UserRating;

    private double UserMaxRating;

    private double ExpertRating;

    private double ExpertMaxRating;

}
