package Compulsory;
import Compulsory.EntityClasses.Genres;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class GenresRepository {

    private EntityManagerClass emc = EntityManagerClass.getInstance();

    public GenresRepository() {}

    public  Genres get_by_id(long id) {
        EntityManager em = emc.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        Genres genre = em.find(Genres.class, id);
        em.getTransaction().commit();

        em.close();
        return genre;
    }

    public Genres get_by_name(String name) {
        EntityManager em = emc.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        Genres genre = em.find(Genres.class, name);
        em.getTransaction().commit();

        em.close();

        return genre;
    }

    public void add(Genres genre) {
        EntityManager em = emc.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.getTransaction().begin();
        em.persist(genre);
        em.getTransaction().commit();

        em.close();
    }
}
