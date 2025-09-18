package app;

import app.config.HibernateConfig;
import app.converters.ActorConverter;
import app.converters.MovieConverter;
import app.daos.ActorDAO;
import app.daos.DirectorDAO;
import app.daos.GenreDAO;
import app.daos.MovieDAO;
import app.dtos.ActorDTO;
import app.dtos.MovieDTO;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
import app.services.ActorServices;
import app.services.DirectorServices;
import app.services.GenreServices;
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

        ActorServices actorServices = new ActorServices();
        ActorDAO actorDAO = new ActorDAO(emf);

        GenreServices genreServices = new GenreServices();
        GenreDAO genreDAO = new GenreDAO(emf);

        DirectorServices directorServices = new DirectorServices();
        DirectorDAO directorDAO = new DirectorDAO(emf);

        
        List<MovieDTO> allDanishMovies = movieServices.fetchDanishMovies(apiKey);

        List<Movie> movies = movieConverter.convertToEntity(allDanishMovies);



        List<Actor> allActors = actorServices.fetchAllActors(apiKey, movies);
        actorDAO.createActor(allActors);
        movieDAO.createMovies(movies);

        movies.forEach(movie -> {
            movie.addActor(apiKey);
        });



        List<Genre> allGenres = genreServices.fetchAllGenres(apiKey, movies);
        genreDAO.createGenre(allGenres);

        movies.forEach(movie -> {
            movie.addGenre(apiKey);
        });

        List<Director> allDirectors = directorServices.fetchDirector(apiKey, movies);
        directorDAO.createDirector(allDirectors);

        movies.forEach(movie -> {
            movie.addDirector(apiKey);
        });




        movieDAO.updateMovies(movies);


 

    }
}