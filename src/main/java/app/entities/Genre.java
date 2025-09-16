package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "genre")
public class Genre {

    @Id
    private int id;

    private String genreName;


    @ManyToMany(mappedBy = "genres")
    private Set<Movie> movies = new HashSet<>();
}
