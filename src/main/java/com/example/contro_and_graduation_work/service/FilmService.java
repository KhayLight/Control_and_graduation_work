package com.example.contro_and_graduation_work.service;

import com.example.contro_and_graduation_work.dao.FilmDao;
import com.example.contro_and_graduation_work.model.Film;
import com.example.contro_and_graduation_work.model.FilmParametersDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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


    public List<Film> findAll(FilmParametersDto filmParametersDto) {
        List<Film> films = kinopoiskApiUnofficialClient.getFilms(filmParametersDto);
        saveAll(films);
        return films;
    }


    public List<Film> findFromDatabase(FilmParametersDto filmParametersDto, PageRequest pageRequest) {
        List<Film> films = findFilmByFilmNameAndYearAndRating(filmParametersDto, pageRequest);
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

    List<Film> findFilmByFilmNameAndYearAndRating(FilmParametersDto filmParametersDto, PageRequest pageRequest) {

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
        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()]))/*predicates.toArray(new Predicate[0])*/);
        cq.orderBy(cb.desc(film.get("id")));

        List<Film> result = entityManager.createQuery(cq).setFirstResult((int) pageRequest.getOffset()).setMaxResults(pageRequest.getPageSize()).getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Film> filmsRootCount = countQuery.from(Film.class);
        countQuery.select(cb.count(filmsRootCount)).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        Long count = result.stream().count();

        Page<Film> result1 = new PageImpl<>(result, pageRequest, count);

        return result1.getContent();
    }
}
