package com.example.contro_and_graduation_work.service;

import com.example.contro_and_graduation_work.model.Film;
import com.example.contro_and_graduation_work.model.FilmParametersDto;
import com.example.contro_and_graduation_work.model.FilmsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KinopoiskApiUnofficialClient {

    /*@Qualifier("apiRestTemplate")*/
    private final RestTemplate restTemplate;

    @Value("${api.key}")
    private String apiKey;

    private String URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films";


    private String getURI(FilmParametersDto filmParametersDto) {
        String uriComponentsBuilder = UriComponentsBuilder.fromUriString(URL)
                // .queryParam("countries", filmParametersDto.getCountries())
                // .queryParam("genres", filmParametersDto.getGenres())
                // .queryParam("order", filmParametersDto.getOrder())
                //   .queryParam("type", filmParametersDto.getType())
                .queryParam("ratingFrom", filmParametersDto.getRatingFrom())
                .queryParam("ratingTo", filmParametersDto.getRatingTo())
                .queryParam("yearFrom", filmParametersDto.getYearFrom())
                .queryParam("yearTo", filmParametersDto.getYearTo())
                .queryParam("keyword", filmParametersDto.getKeyword())
                .queryParam("page", filmParametersDto.getPage())
                .build().toUriString();
        return uriComponentsBuilder;
    }

    public List<Film> getFilms(FilmParametersDto filmParametersDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set("X-API-KEY", apiKey);

        HttpEntity<FilmsDto> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<FilmsDto> response = restTemplate.exchange(getURI(filmParametersDto), HttpMethod.GET, httpEntity, FilmsDto.class);
        System.out.println(getURI(filmParametersDto));
        /*FilmsDto response = restTemplate.getForObject(getURI(filmDto), FilmsDto.class);
       return response.getItems();*/
        return loadDescriptions(response.getBody().getItems());
    }

    public List<Film> loadDescriptions(List<Film> list) {

        for (Film film : list) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            httpHeaders.set("X-API-KEY", apiKey);

            HttpEntity<FilmsDto> httpEntity = new HttpEntity<>(httpHeaders);

            ResponseEntity<Film> response = restTemplate.exchange(URL + "/" + film.getFilmId(), HttpMethod.GET, httpEntity, Film.class);
            film.setDescription(response.getBody().getDescription());

        }
        return list;

    }


}
