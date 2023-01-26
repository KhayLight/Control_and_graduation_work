package com.example.contro_and_graduation_work.model;

import lombok.Data;

import java.util.List;

@Data
public class FilmParametersDto {
    /*private List<Integer> countries;
    private List<Integer> genres;
    private OrderSortFilter order;*/
    /*   private FilmTypeFilter type;*/
    private Integer ratingFrom;
    private Integer ratingTo;
    private Integer yearFrom;
    private Integer yearTo;
    private String keyword;
    private Integer page;
}
