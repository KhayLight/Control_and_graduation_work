package com.example.contro_and_graduation_work.model;

import jakarta.persistence.*;
import lombok.*;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "film")
public class Film implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;


    @Column(name = "film_id")
    private Integer filmId;
    @Column(name = "film_name")
    private String filmName;
    @Column(name = "year")
    private Integer year;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "description")
    private String description;


    public Film(Long id, Integer filmId, String filmName, Integer year, Double rating, String description) {
        this.id = id;
        this.filmId = filmId;
        this.filmName = filmName;
        this.year = year;
        this.rating = rating;
        this.description = description;

    }


    public JSONObject jsonObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("filmId",filmId);
        jsonObject.put("filmName",id);
        jsonObject.put("year",year);
        jsonObject.put("rating",rating);
        jsonObject.put("description",description);


        return jsonObject;


    }

}
