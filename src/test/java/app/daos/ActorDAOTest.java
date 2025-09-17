package app.daos;

import app.config.HibernateConfig;
import app.entities.Actor;
import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import populators.ActorPopulator;
import populators.MoviePopulator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActorDAOTest {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static MovieDAO movieDAO = new MovieDAO(emf);
    private static ActorDAO actorDAO = new ActorDAO(emf);
    private static List<Movie> movieList;
    private static List<Actor> actorList;
    private static List<Integer> movieIds;


    @BeforeEach
    void setUp (){
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Movie").executeUpdate();
            em.getTransaction().commit();
            movieList = MoviePopulator.populateMovies(movieDAO);
            movieDAO.createMovies(movieList);
            movieIds = movieDAO.getMovieIds();

            actorList = ActorPopulator.populateActors(movieIds);
            actorDAO.createActor(actorList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    //Also indirectly tests getAll()
    void createActor() {
        List<Actor> actorTest = actorDAO.getAll();

        assertTrue(1000 < actorTest.size());
    }
}