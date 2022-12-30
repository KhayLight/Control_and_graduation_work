package com.example.contro_and_graduation_work.service;

import com.example.contro_and_graduation_work.model.Film;
import com.example.contro_and_graduation_work.model.FilmDto;
import com.example.contro_and_graduation_work.model.FilmsDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class KinopoiskApiUnofficialClient {

    private RestTemplate restTemplate = new RestTemplate();

    public List<FilmDto> getFilms() {
        String url = "https://kinopoiskapiunofficial.tech/api/v2.2/films?countries=1&genres=2&order=RATING&type=ALL&ratingFrom=0&ratingTo=10&yearFrom=1000&yearTo=3000&page=1";
        try {
            FilmsDto response = restTemplate.getForObject(new URI(url), FilmsDto.class);
            return response.getItems();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
