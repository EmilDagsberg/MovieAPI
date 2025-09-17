package app.services;

import app.converters.ActorConverter;
import app.daos.MovieDAO;
import app.dtos.ActorDTO;
import app.dtos.ActorResponseDTO;
import app.dtos.MoviesResponseDTO;
import app.entities.Actor;
import app.entities.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActorServices {
    public List<Actor> fetchAllActors(String apiKey, List<Movie> danishMovies) {
        Set<Actor> actors = new HashSet<>();
        ActorConverter actorConverter = new ActorConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        int loop = 0;


        // Fetch data from api

        HttpClient httpClient = HttpClient.newHttpClient();

        // Create a request
        try {
            do {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://api.themoviedb.org/3/movie/" + danishMovies.get(loop).getId() + "/credits?language=en-US"))
                        .header("Authorization", "Bearer " + apiKey)
                        .GET()
                        .build();

                // Send the request and get the response
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    String json = response.body();
                    ActorResponseDTO discoverResponse = objectMapper.readValue(json, ActorResponseDTO.class);

                    if (discoverResponse.getResults() != null && !discoverResponse.getResults().isEmpty()) {
                        List<Actor> foundActors = actorConverter.convertToEntity(discoverResponse.getResults());
                        actors.addAll(foundActors);


                    }
                    loop++;



                } else {
                    System.out.println("Fejl ved læsning af Movie-API");
                    System.out.println("Fejl: " + response.body());
                    break;
                }
            } while (loop < danishMovies.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(actors);
    }

}
