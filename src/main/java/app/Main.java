package app;

import app.dtos.MovieDTO;
import app.services.MovieServices;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Movie API output >>>>>>>>>>");
        String apiKey = System.getenv("API_KEY");

        MovieServices movieServices = new MovieServices();

        MovieDTO movieDTO = movieServices.getMovieById("tt23289160", apiKey);

        //System.out.println(movieDTO);

        //movieServices.getByRating(8.5, 9.0, 2, apiKey).forEach(System.out::println);

        movieServices.getSortedByReleaseDate(2, apiKey).forEach(System.out::println);

    }
}