package app.services;

import app.converters.DirectorConverter;
import app.dtos.ActorResponseDTO;
import app.dtos.DirectorResponseDTO;
import app.entities.Actor;
import app.entities.Director;
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

public class DirectorServices {
    public List<Director> fetchDirector(String apiKey, List<Movie> danishMovies) {
        Set<Director> directors = new HashSet<>();
        DirectorConverter directorConverter = new DirectorConverter();
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
                    DirectorResponseDTO discoverResponse = objectMapper.readValue(json, DirectorResponseDTO.class);

                    if (discoverResponse.getDirectors() != null && !discoverResponse.getDirectors().isEmpty()) {
                        Director foundDirector = directorConverter.convertToEntity(discoverResponse.getDirectors().get(0));
                        directors.add(foundDirector);


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

        return new ArrayList<>(directors);
    }

    public Director fetchDirectorById(int movieId, String apiKey) {
        Director director = null;
        DirectorConverter directorConverter = new DirectorConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());


        // Fetch data from api

        HttpClient httpClient = HttpClient.newHttpClient();

        // Create a request
        try {

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://api.themoviedb.org/3/movie/" + movieId + "/credits?language=en-US"))
                        .header("Authorization", "Bearer " + apiKey)
                        .GET()
                        .build();

                // Send the request and get the response
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    String json = response.body();
                    DirectorResponseDTO discoverResponse = objectMapper.readValue(json, DirectorResponseDTO.class);

                    if (discoverResponse.getDirectors() != null && !discoverResponse.getDirectors().isEmpty()) {
                        Director foundDirector = directorConverter.convertToEntity(discoverResponse.getDirectors().get(0));
                        director = foundDirector;


                    }



                } else {
                    System.out.println("Fejl ved læsning af Movie-API");
                    System.out.println("Fejl: " + response.body());
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return director;
    }
}
