package com.example.contro_and_graduation_work.model;

import lombok.Data;

@Data
public class FilmDto {
    private Integer kinopoiskId;
    private String nameRu;
    private Integer year;
    private Integer ratingKinopoisk;
    private String description;
}