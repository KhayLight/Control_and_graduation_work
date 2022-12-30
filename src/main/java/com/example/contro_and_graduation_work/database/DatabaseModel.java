package com.example.contro_and_graduation_work.database;

import com.example.contro_and_graduation_work.hibernate.HibernateUtils;
import com.example.contro_and_graduation_work.model.Film;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@NoArgsConstructor
public class DatabaseModel {

    public void saveOrNotFilm(Film film) {
        if (getFilmByKinopoiskId(film.getFilmId()) != null) return;

        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(film);
        session.getTransaction().commit();
    }

    public Film getFilmByKinopoiskId(Integer id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        // session.beginTransaction();
        Film film = session.get(Film.class, id);
        Hibernate.initialize(film);
        /*Query sessionQuery = session.createQuery("FROM Film f WHERE f.filmId = "+id);
        sessionQuery.setMaxResults(1);
        Film film = (Film)sessionQuery.uniqueResult();
       // List<Film> films = sessionQuery.getResultList();
        Hibernate.initialize(film);
        session.getTransaction().commit();
        session.close();

         */
        return film;//films.get(0);
    }

    public List<Film> searchByWord(String text, Double[] rank, Integer[] year) {
        double minimal_rank = rank[0];
        double maximal_rank = rank[1];

        int minimal_year = year[0];

        int maximal_year = year[1];

        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query sessionQuery = session.createQuery("FROM Film f WHERE (f.filmName LIKE :text OR f.description LIKE :text2) AND f.rating >= " + minimal_rank + " AND f.rating <= " + maximal_rank + " AND f.year >= " + minimal_year + " AND f.year <= " + maximal_year);


        sessionQuery.setParameter("text", "%" + text + "%");
        sessionQuery.setParameter("text2", "%" + text + "%");

        List<Film> films = (List<Film>) sessionQuery.getResultList();


        session.getTransaction().commit();
        return films;

    }
}
