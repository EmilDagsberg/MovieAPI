package app;

import app.config.HibernateConfig;
import app.converters.ActorConverter;
import app.converters.MovieConverter;
import app.daos.ActorDAO;
import app.daos.MovieDAO;
import app.dtos.ActorDTO;
import app.dtos.MovieDTO;
import app.entities.Actor;
import app.entities.Movie;
import app.services.ActorServices;
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
        ActorConverter actorConverter = new ActorConverter();
        ActorDAO actorDAO = new ActorDAO(emf);

        
        List<MovieDTO> allDanishMovies = movieServices.fetchDanishMovies(apiKey);
        System.out.println(allDanishMovies.size());

        List<Movie> movies = movieConverter.convertToEntity(allDanishMovies);
        movieDAO.createMovies(movies);

        List<Integer> movieIds = movieDAO.getMovieIds();
        List<ActorDTO> allActors = actorServices.fetchAllActors(apiKey, movieIds);
        List<Actor> actors = actorConverter.convertToEntity(allActors);
        actorDAO.createActor(actors);

 

    }
}