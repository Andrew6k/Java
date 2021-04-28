package Compulsory;

import Compulsory.EntityClasses.Movies;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class MoviesRepository {
    private EntityManagerClass emc = EntityManagerClass.getInstance();

    //Constructor
    public MoviesRepository() {}

    //Methods
    public void create(Movies movie) {
        EntityManager em = emc.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        em.persist(movie);
        et.commit();

        em.close();

        System.out.println("Filmul cu ID-ul " + movie.getId() + " a fost inserat in baza de date!");
    }
    public Movies findById(int ID) {
        EntityManager em = emc.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        Movies movie = em.find(Movies.class, ID);

        et.commit();
        em.close();

        return movie;
    }
    public List<Movies> findByName(String name) {
        EntityManager em = emc.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        List<Movies> movies = em.createNamedQuery("Movies.findByName")
                .setParameter("title", "%" + name + "%")
                .getResultList();

        et.commit();
        em.close();

        return movies;
    }
    public void remove(int ID) {
        EntityManager em = emc.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        Movies movieToRemove = findById(ID);

        em.remove(em.contains(movieToRemove) ? movieToRemove : em.merge(movieToRemove));
        System.out.println("Filmul cu ID-ul " + ID + " a fost sters din baza de date!");
        et.commit();

        em.close();
    }
}
