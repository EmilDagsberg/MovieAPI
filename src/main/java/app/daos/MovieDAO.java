package app.daos;

import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class MovieDAO {
    private final EntityManagerFactory emf;

    public MovieDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void createMovies(List<Movie> danishMovies) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            danishMovies.forEach(movie -> em.persist(movie));
            em.getTransaction().commit();
            em.close();
        }
    }

    public List<Integer> getMovieIds() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Integer> query = em.createQuery("SELECT m.id FROM Movie m", Integer.class);
            return query.getResultList();

        }
    }

    public List<Movie> getMoviesByDirector(String directorName) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Query q = em.createQuery("SELECT m FROM Movie m WHERE m.director.name = :directorName", Movie.class);
            q.setParameter("directorName", directorName);
            return q.getResultList();
        }
    }

    public Movie getMovieById(Integer movieId) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Movie.class, movieId);
        }
    }

    public List<Movie> getAll(){
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m", Movie.class)
                    .getResultList();
        }
    }
}
