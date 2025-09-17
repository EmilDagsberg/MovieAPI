package app.daos;

import app.entities.Actor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ActorDAO {
    private final EntityManagerFactory emf;

    public ActorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void createActor(List<Actor> actors) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            actors.forEach(actor -> em.merge(actor));
            em.getTransaction().commit();
            em.close();
        }
    }
}
