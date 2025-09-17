package app.daos;

import app.entities.Genre;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class GenreDAO {
    private final EntityManagerFactory emf;

    public GenreDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void createGenre(List<Genre> genre) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            genre.forEach(genre1 -> em.merge(genre1));
            em.getTransaction().commit();
            em.close();
        }
    }

    public List<String> getAllGenres() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            TypedQuery<String> query = em.createQuery("SELECT g FROM Genre g", String.class);
            return query.getResultList();
        }
    }

    public List<Integer> getGenreIds() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            TypedQuery<Integer> query = em.createQuery("SELECT g.id FROM Genre g", Integer.class);
            return query.getResultList();
        }
    }

    public void updateGenre(Genre genre) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(genre);
            em.getTransaction().commit();
            em.close();
        }
    }

    public void deleteGenre(Genre genre) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(genre);
            em.getTransaction().commit();
            em.close();
        }
    }


}
