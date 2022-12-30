package com.example.contro_and_graduation_work.model;

import lombok.Data;

import java.util.List;
@Data
public class FilmDto {
    private Integer kinopoiskId;
    private String imdbId;
    private String nameRu;
    private String nameEn;
    private String nameOriginal;
    private List<String> countries;
    private String genres;
    private Integer ratingKinopoisk;
    private Double ratingImdb;
    private Integer year;
    private String type;
    private String posterUrl;
    private String posterUrlPreview;
}
