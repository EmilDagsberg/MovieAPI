package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "movie")
public class Movie {

    @Id
    private int id;

    private String title;

    private LocalDate releaseDate;

    private String originalLanguage;

    private double voteAverage;


    // Relations M:M
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "m_g_link",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();


    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "m_a_link",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    // Relations M:1
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "director_id")
    private Director director;


    public void addActor(Actor actor) {
        this.actors.add(actor);

    }
}
