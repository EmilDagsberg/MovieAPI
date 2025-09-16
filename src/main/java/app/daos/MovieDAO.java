package app.daos;

import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

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


}
