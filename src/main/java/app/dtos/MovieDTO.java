package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // ignorer json properties der ikke er i denne dto
public class MovieDTO {
    private String title;
    @JsonProperty("original_title")
    private String originalTitle;
    private String overview;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("release_date")
    private LocalDate releaseDate;
    @JsonProperty("vote_average")
    private double voteAverage;

    public String getReleaseDate() {
        return releaseDate.toString();
    }
}
