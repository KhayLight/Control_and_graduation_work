package com.example.contro_and_graduation_work.service;

import com.example.contro_and_graduation_work.config.FilmMapper;
import com.example.contro_and_graduation_work.dao.FilmDao;
import com.example.contro_and_graduation_work.model.Film;
import com.example.contro_and_graduation_work.model.FilmParametersDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FilmService {


    public final JavaMailSender javaMailSender;
    private final EntityManager entityManager;
    private final FilmDao filmDao;
    private final KinopoiskApiUnofficialClient kinopoiskApiUnofficialClient;

    private FilmMapper filmMapper;


    public List<Film> findAll(FilmParametersDto filmParametersDto) {

        List<Film> films = kinopoiskApiUnofficialClient.getFilms(filmParametersDto);
        saveAll(films);

        return films;
    }


    public List<Film> findFromDatabase(FilmParametersDto filmParametersDto) {

        List<Film> films = findFilmByFilmNameAndYearAndRating(filmParametersDto);
        if (films.size() < 5) {
            findAll(filmParametersDto);
        }
        films = findFilmByFilmNameAndYearAndRating(filmParametersDto);

        return films;
    }

    public void saveFilm(Film film) {
        if (!filmDao.existsByFilmId(film.getFilmId())) {
            filmDao.save(film);
        }
    }


    public void saveAll(List<Film> filmList) {
        for (Film film : filmList) {
            saveFilm(film);
        }
    }

    List<Film> findFilmByFilmNameAndYearAndRating(FilmParametersDto filmParametersDto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Film> cq = cb.createQuery(Film.class);

        Root<Film> film = cq.from(Film.class);
        List<Predicate> predicates = new ArrayList<>();


        if (filmParametersDto.getKeyword() != null) {
            predicates.add(cb.like(film.get("filmName"), "%" + filmParametersDto.getKeyword() + "%"));
        }

        if (filmParametersDto.getYearTo() != null) {
            predicates.add(cb.lessThan(film.get("year"), filmParametersDto.getYearTo()));
        }
        if (filmParametersDto.getYearFrom() != null) {
            predicates.add(cb.greaterThan(film.get("year"), filmParametersDto.getYearFrom()));
        }

        if (filmParametersDto.getRatingTo() != null) {
            predicates.add(cb.lessThan(film.get("rating"), filmParametersDto.getRatingTo()));
        }
        if (filmParametersDto.getRatingFrom() != null) {
            predicates.add(cb.greaterThan(film.get("rating"), filmParametersDto.getRatingFrom()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }
}
