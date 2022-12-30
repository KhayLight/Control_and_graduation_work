package com.example.contro_and_graduation_work.service;

import com.example.contro_and_graduation_work.dao.FilmDao;
import com.example.contro_and_graduation_work.model.Film;
import com.example.contro_and_graduation_work.model.FilmDto;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class FilmService {

    @Autowired
    private FilmDao filmDao;
    @Autowired
    private KinopoiskApiUnofficialClient kinopoiskApiUnofficialClient;

    public List<Film> findAll() {
        return filmDao.findAll(); /*kinopoiskApiUnofficialClient.getFilms().stream()
                .map(this::toFilm)
                .collect(collectingAndThen(toList(), ImmutableList::copyOf));*/
    }

    private Film toFilm(@NonNull FilmDto input) {
        return null;/*new Film(input.getKinopoiskId(),
                input.getKinopoiskId(),
                input.getNameRu(),
                input.getYear(),
                input.getRatingImdb(),
                input.getGenres());
                */
    }

}
