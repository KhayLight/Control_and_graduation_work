package com.example.contro_and_graduation_work.config;

import com.example.contro_and_graduation_work.model.Film;
import com.example.contro_and_graduation_work.model.FilmDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface FilmMapper {

    @Mapping(target = "description", source = "description")
    @Mapping(target = "filmName", source = "nameRu")
    @Mapping(target = "year", source = "year")
    @Mapping(target = "rating", source = "ratingKinopoisk")
    @Mapping(target = "filmId", source = "kinopoiskId")
    Film toFilm(FilmDto filmDto);


}
