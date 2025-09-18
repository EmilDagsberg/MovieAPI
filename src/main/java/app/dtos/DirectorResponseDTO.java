package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectorResponseDTO {
    @JsonProperty("crew")
    private List<DirectorDTO> results;

    public List<DirectorDTO> getDirectors() {
        if (results == null) {
            return List.of();
        }
        return results.stream()
                .filter(crew -> "Director".equalsIgnoreCase(crew.getJob()))
                .toList();
    }
}
