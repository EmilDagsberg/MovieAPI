package populators;

import app.converters.ActorConverter;
import app.daos.MovieDAO;
import app.dtos.ActorDTO;
import app.entities.Actor;
import app.entities.Movie;
import app.services.ActorServices;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ActorPopulator {

    public static List<Actor> populateActors (List<Movie> movies) {
        String apiKey = System.getenv("API_KEY");

        ActorServices actorService = new ActorServices();
        ActorConverter actorConverter = new ActorConverter();

        return actorService.fetchAllActors(apiKey, movies);
    }
}
