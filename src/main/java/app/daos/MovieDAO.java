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

    public void updateMovies(List<Movie> danishMovies) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            danishMovies.forEach(movie -> em.merge(movie));
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

    // Returns a list of movies made by a given director name
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

    // Returns a list of movies with a given genre
    public List<Movie> getMoviesByGenre(String genre) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m JOIN m.genres g WHERE g.genreName = :genreName", Movie.class);
            query.setParameter("genreName", genre);
            return query.getResultList();
        }
    }

    // Returns a list of movies with given search-word (Case-insensitive)
    public List<Movie> getMoviesByTitle(String title) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m WHERE LOWER(m.title) LIKE :title", Movie.class);
            query.setParameter("title", "%" + title.toLowerCase() + "%");
            return query.getResultList();
        }
    }

    public double getTotalAverageRating() {
        try (EntityManager em = emf.createEntityManager()) {
            Double avg = em.createQuery(
                    "SELECT AVG(m.voteAverage) FROM Movie m", Double.class
            ).getSingleResult();
            return avg;
        }
    }

    // Sorts list by descending and takes top 10 from list
    public List<Movie> getTop10Movies() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m ORDER BY m.voteAverage DESC", Movie.class
            );
            query.setMaxResults(10); // Make list only be 10 long
            return query.getResultList();
        }
    }

    // Sorts list by ascending and takes top 10 from list
    public List<Movie> getBot10Movies() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m ORDER BY m.voteAverage ASC", Movie.class
            );
            query.setMaxResults(10);
            return query.getResultList();
        }
    }


}
