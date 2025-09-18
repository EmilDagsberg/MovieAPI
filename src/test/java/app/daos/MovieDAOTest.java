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
        movieDAO.createMovies(movieList);
        List<Movie> moviesByDirector = movieDAO.getMoviesByDirector("Bille August");

        assertTrue(moviesByDirector != null &&
                moviesByDirector.stream().allMatch(movie -> movie.getDirector().getName().toLowerCase().contains("bille august")));
    }

    @Test
    void getMovieById() {
        movieDAO.createMovies(movieList);
        m1 = movieDAO.getMovieById(663870);
        assertEquals("Retfærdighedens Ryttere", m1.getTitle());
    }

    @Test
    void getMoviesByGenre() {
        movieDAO.createMovies(movieList);
        List<Movie> moviesByGenre = movieDAO.getMoviesByGenre("Horror");

        assertTrue(moviesByGenre != null && moviesByGenre.stream().allMatch(movie -> movie.getGenres()
                .stream().allMatch(genre -> genre.getGenreName().toLowerCase().contains("horror"))));
    }

    @Test
    void getMoviesByTitle() {
        movieDAO.createMovies(movieList);
        List<Movie> movies = movieDAO.getMoviesByTitle("De");

        if(!movies.isEmpty()){
            assertTrue(movies.stream()
                    .allMatch(movie -> movie.getTitle() != null && movie.getTitle().toLowerCase().contains("de")));
        }
    }

    @Test
    void getTotalAverageRating() {
    }

    @Test
    void getTop10Movies() {
        movieDAO.createMovies(movieList);
        List<Movie> topTen = movieDAO.getTop10Movies();

        assertEquals(10, topTen.size());
    }

    @Test
    void getBot10Movies() {
        movieDAO.createMovies(movieList);
        List<Movie> topTen = movieDAO.getBot10Movies();

        assertEquals(10, topTen.size());
    }
}