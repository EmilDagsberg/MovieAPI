package populators;

import app.converters.ActorConverter;
import app.daos.MovieDAO;
import app.dtos.ActorDTO;
import app.entities.Actor;
import app.services.ActorServices;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ActorPopulator {

    public static List<Actor> populateActors (List<Integer> movieIds) {
        String apiKey = System.getenv("API_KEY");

        ActorServices actorService = new ActorServices();
        ActorConverter actorConverter = new ActorConverter();

        List<ActorDTO> actorDTOS = actorService.fetchAllActors(apiKey, movieIds);
        return actorConverter.convertToEntity(actorDTOS);
    }
}
