package app.entities;

import app.services.ActorServices;
import app.services.DirectorServices;
import app.services.GenreServices;
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
    @Builder.Default
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "m_g_link",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();


    @Builder.Default
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "m_a_link",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    // Relations M:1
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "director_id")
    private Director director;


    public void addActor(String apiKey) {
        ActorServices actorServices = new ActorServices();
        List<Actor> foundActors = actorServices.fetchActorsById(this.getId(), apiKey);
        foundActors.forEach(actor -> actors.add(actor));
    }

    public void addGenre(String apiKey) {
        GenreServices genreServices = new GenreServices();
        List<Genre> foundGenres = genreServices.fetchGenreById(this.getId(), apiKey);
        foundGenres.forEach(genre -> genres.add(genre));
    }

    public void addDirector(String apiKey) {
        DirectorServices directorServices = new DirectorServices();
        Director foundDirector = directorServices.fetchDirectorById(this.getId(), apiKey);
        this.director = foundDirector;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", voteAverage=" + voteAverage +
                ", director=" + (director != null ? director.getName() : "null") +
                '}';
    }

}
