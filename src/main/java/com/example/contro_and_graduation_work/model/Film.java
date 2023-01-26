package com.example.contro_and_graduation_work.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name = "film")
public class Film implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "film_id")
    @JsonProperty("kinopoiskId")
    private Integer filmId;
    @Column(name = "filmName")
    @JsonProperty("nameRu")
    private String filmName;
    @Column(name = "year")
    @JsonProperty("year")
    private Integer year;
    @Column(name = "rating")
    @JsonProperty("ratingKinopoisk")
    private Double rating;
    @Column(name = "description")
    @JsonProperty("description")
    private String description;


    public Film(Long id, Integer filmId, String filmName, Integer year, Double rating, String description) {
        this.id = id;
        this.filmId = filmId;
        this.filmName = filmName;
        this.year = year;
        this.rating = rating;
        this.description = description;

    }

    public JSONObject jsonObject() throws JsonProcessingException {
        Film film = new Film(id, filmId, filmName, year, rating, description);
        ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonObject = new JSONObject(mapper.writeValueAsString(film));
        return jsonObject;
    }

}
