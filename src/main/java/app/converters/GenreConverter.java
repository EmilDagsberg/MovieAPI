package app.converters;

import app.dtos.GenreDTO;
import app.entities.Genre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GenreConverter {

    public List<Genre> convertToEntity(List<GenreDTO> dtos) {
        List<Genre> genreEntities = new ArrayList<>();
        dtos.forEach(dto -> genreEntities.add(
                Genre.builder()
                        .id(dto.getId())
                        .genreName(dto.getGenreName())
                        .build()
        ));
        return genreEntities;
    }
}
