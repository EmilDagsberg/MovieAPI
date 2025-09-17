package app.services;

import app.converters.ActorConverter;
import app.converters.GenreConverter;
import app.dtos.ActorResponseDTO;
import app.dtos.GenreResponseDTO;
import app.entities.Actor;
import app.entities.Genre;
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

public class GenreServices {
    public List<Genre> fetchAllGenres(String apiKey, List<Movie> danishMovies) {
        Set<Genre> genres = new HashSet<>();
        GenreConverter genreConverter = new GenreConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        int loop = 0;


        // Fetch data from api

        HttpClient httpClient = HttpClient.newHttpClient();

        // Create a request
        try {
            do {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://api.themoviedb.org/3/movie/"+ danishMovies.get(loop).getId() + "?language=en-US"))
                        .header("Authorization", "Bearer " + apiKey)
                        .GET()
                        .build();

                // Send the request and get the response
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    String json = response.body();
                    GenreResponseDTO discoverResponse = objectMapper.readValue(json, GenreResponseDTO.class);

                    if (discoverResponse.getResults() != null && !discoverResponse.getResults().isEmpty()) {
                        List<Genre> foundGenres = genreConverter.convertToEntity(discoverResponse.getResults());
                        genres.addAll(foundGenres);


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

        return new ArrayList<>(genres);
    }

    public List<Genre> fetchGenreById(int movieId, String apiKey) {
        List<Genre> genres = new ArrayList<>();
        GenreConverter genreConverter = new GenreConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        // Fetch data from api

        HttpClient httpClient = HttpClient.newHttpClient();

        // Create a request
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.themoviedb.org/3/movie/"+ movieId + "?language=en-US"))
                    .header("Authorization", "Bearer " + apiKey)
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String json = response.body();
                GenreResponseDTO discoverResponse = objectMapper.readValue(json, GenreResponseDTO.class);

                if (discoverResponse.getResults() != null && !discoverResponse.getResults().isEmpty()) {
                    List<Genre> foundGenres = genreConverter.convertToEntity(discoverResponse.getResults());
                    genres.addAll(foundGenres);

                }




            } else {
                System.out.println("Fejl ved læsning af Movie-API");
                System.out.println("Fejl: " + response.body());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(genres);
    }

}
