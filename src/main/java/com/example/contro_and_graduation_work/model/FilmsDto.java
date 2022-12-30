package com.example.contro_and_graduation_work.model;

import lombok.Data;

import java.util.List;
@Data
public class FilmsDto {
    private Long total;
    private Long totalPages;
    private List<FilmDto> items;
}
