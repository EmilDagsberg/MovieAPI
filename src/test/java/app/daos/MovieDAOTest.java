package app.daos;

import app.config.HibernateConfig;
import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import populators.MoviePopulator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class MovieDAOTest {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static MovieDAO movieDAO = new MovieDAO(emf);
    private static List<Movie> movieList;
    private static Movie m1;

    @BeforeEach
    void setUp (){
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Movie").executeUpdate();
            em.getTransaction().commit();
            movieList = MoviePopulator.populateMovies(movieDAO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createMovies() {
        movieDAO.createMovies(movieList);

        List<Movie> movies = movieDAO.getAll();
        assertTrue(movies.size() > 1000);
    }

    @Test
    void getMovieIds() {
        List<Integer> moviesById = movieDAO.getMovieIds();
        assertTrue(moviesById.size() > 1000);
    }

    @Test
    void getMoviesByDirector() {
//        movieDAO.getMoviesByDirector("");
    }

    @Test
    void getMovieById() {
        movieDAO.createMovies(movieList);
        m1 = movieDAO.getMovieById(663870);
        assertEquals("Riders of Justice", m1.getTitle());
    }
}