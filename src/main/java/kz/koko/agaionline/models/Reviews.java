package kz.koko.agaionline.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String reviewername;
    private String revieweremail;
    private String reviewtext;
    private String reviewdate;

    public Reviews(String reviewername, String revieweremail, String reviewtext, String reviewdate) {
        this.reviewername = reviewername;
        this.revieweremail = revieweremail;
        this.reviewtext = reviewtext;
        this.reviewdate = reviewdate;
    }
}
