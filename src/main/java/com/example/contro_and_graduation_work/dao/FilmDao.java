package com.example.contro_and_graduation_work.dao;

import com.example.contro_and_graduation_work.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmDao extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {
boolean existsByFilmId(Integer filmId);


}
