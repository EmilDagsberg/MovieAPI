package app;

import app.config.HibernateConfig;
import app.converters.MovieConverter;
import app.daos.MovieDAO;
import app.dtos.MovieDTO;
import app.entities.Movie;
import app.services.MovieServices;
import jakarta.persistence.EntityManagerFactory;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();


        System.out.println("Movie API output >>>>>>>>>>");
        String apiKey = System.getenv("API_KEY");

        MovieServices movieServices = new MovieServices();
        MovieConverter movieConverter = new MovieConverter();
        MovieDAO movieDAO = new MovieDAO(emf);
        //MovieDTO movieDTO = movieServices.getMovieById("tt23289160", apiKey);
        //System.out.println(movieDTO);
        //movieServices.getByRating(8.5, 9.0, 2, apiKey).forEach(System.out::println);
        
        List<MovieDTO> allDanishMovies = movieServices.fetchDanishMovies(apiKey);
        System.out.println(allDanishMovies.size());

        List<Movie> movies = movieConverter.convertToEntity(allDanishMovies);
        movieDAO.createMovies(movies);


        // movieServices.getSortedByReleaseDate(2, apiKey).forEach(System.out::println);
 

    }
}