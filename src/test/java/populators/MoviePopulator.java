package populators;

import app.config.HibernateConfig;
import app.converters.MovieConverter;
import app.daos.MovieDAO;
import app.dtos.MovieDTO;
import app.entities.Movie;
import app.services.MovieServices;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class MoviePopulator {
    public static List<Movie> populateMovies (MovieDAO dao){
        MovieServices movieServices = new MovieServices();
        MovieConverter movieConverter = new MovieConverter();
        String apiKey = System.getenv("API_KEY");

        List<MovieDTO> movieDTOS = movieServices.fetchDanishMovies(apiKey);
        return movieConverter.convertToEntity(movieDTOS);
    }

}
