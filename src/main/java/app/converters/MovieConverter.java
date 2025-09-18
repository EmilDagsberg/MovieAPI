package app.converters;

import app.dtos.MovieDTO;
import app.entities.Movie;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieConverter {
    public List<Movie> convertToEntity(List<MovieDTO> dtos) {
        List<Movie> movieEntities = new ArrayList<>();
        dtos.forEach(dto -> movieEntities.add(
                Movie.builder()
                        .id(dto.getId())
                        .title(dto.getOriginalTitle())
                        .releaseDate(LocalDate.parse(dto.getReleaseDate()))
                        .originalLanguage(dto.getOriginalLanguage())
                        .voteAverage(dto.getVoteAverage())
                        .build()
        ));
        return movieEntities;
    }
}
