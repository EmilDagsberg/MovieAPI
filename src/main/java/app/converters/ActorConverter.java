package app.converters;

import app.dtos.ActorDTO;
import app.entities.Actor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActorConverter {

    public List<Actor> convertToEntity(List<ActorDTO> dtos) {
        List<Actor> actorEntities = new ArrayList<>();
        dtos.forEach(dto -> actorEntities.add(
                Actor.builder()
                        .id(dto.getId())
                        .name(dto.getActorName())
                        .build()
        ));
        return actorEntities;
    }
}
