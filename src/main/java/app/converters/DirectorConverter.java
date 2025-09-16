package app.converters;

import app.dtos.DirectorDTO;
import app.entities.Director;

public class DirectorConverter {

    public Director director(DirectorDTO directorDTO) {
        return Director.builder()
                .id(directorDTO.getId())
                .name(directorDTO.getDirectorName())
                .build();
    }
}
