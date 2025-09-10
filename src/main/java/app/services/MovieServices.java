package app.services;

import app.dtos.MoviesResponseDTO;
import app.dtos.MovieDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class MovieServices {
    public MovieDTO getMovieById(String movieId, String apiKey) {
        MovieDTO movieDTO = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Fetch data from api

        HttpClient httpClient = HttpClient.newHttpClient();

        // Create a request
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.themoviedb.org/3/find/" + movieId + "?external_source=imdb_id"))
                    .header("Authorization", "Bearer " + apiKey)
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String json = response.body();
                MoviesResponseDTO findResponse = objectMapper.readValue(json, MoviesResponseDTO.class);

                if (findResponse.getResults() != null && !findResponse.getResults().isEmpty()) {
                    movieDTO = findResponse.getResults().get(0);
                }

            } else {
                System.out.println("Fejl ved læsning af Movie-API");
                System.out.println("Fejl: " + response.body());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieDTO;
    }

    public List<MovieDTO> getByRating(double rating1, double rating2, int pageNumber, String apiKey) {
        List<MovieDTO> movieDTO = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Fetch data from api

        HttpClient httpClient = HttpClient.newHttpClient();

        // Create a request
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=" + pageNumber + "&sort_by=popularity.desc&vote_average.gte=" + rating1 + "&vote_average.lte=" + rating2))
                    .header("Authorization", "Bearer " + apiKey)
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String json = response.body();
                MoviesResponseDTO discoverResponse = objectMapper.readValue(json, MoviesResponseDTO.class);

                if (discoverResponse.getResults() != null && !discoverResponse.getResults().isEmpty()) {
                    movieDTO = discoverResponse.getResults();
                }


        } else {
                System.out.println("Fejl ved læsning af Movie-API");
                System.out.println("Fejl: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieDTO;
    }

    public List<MovieDTO> getSortedByReleaseDate(int pageNumber, String apiKey) {
        List<MovieDTO> movieDTO = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Fetch data from api

        HttpClient httpClient = HttpClient.newHttpClient();

        // Create a request
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&page=" + pageNumber + "&sort_by=primary_release_date.desc"))
                    .header("Authorization", "Bearer " + apiKey)
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String json = response.body();
                MoviesResponseDTO discoverResponse = objectMapper.readValue(json, MoviesResponseDTO.class);

                if (discoverResponse.getResults() != null && !discoverResponse.getResults().isEmpty()) {
                    movieDTO = discoverResponse.getResults();
                }


            } else {
                System.out.println("Fejl ved læsning af Movie-API");
                System.out.println("Fejl: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieDTO;
    }
}
