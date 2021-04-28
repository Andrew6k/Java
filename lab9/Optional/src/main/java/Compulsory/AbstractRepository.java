package Compulsory;

import Compulsory.EntityClasses.Movies;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.reflect.Type;
import java.util.List;

public class AbstractRepository<T> {
    private EntityManagerClass emc = EntityManagerClass.getInstance();

    public void create(T t) {
        EntityManager em = emc.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        em.persist(t);
        et.commit();

        em.close();

        //System.out.println("Filmul cu ID-ul " + t.getId() + " a fost inserat in baza de date!");
    }
    public T findById(int ID, Class<T> cls) {
        EntityManager em = emc.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();


        T t = em.find(cls, ID);

        et.commit();
        em.close();

        return t;
    }

    public List<T> findByName(String name) {
        EntityManager em = emc.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        List<T> t = em.createNamedQuery("T.findByName")
                .setParameter("title", "%" + name + "%")
                .getResultList();

        et.commit();
        em.close();

        return t;
    }
    public void remove(int ID,Class<T> cls) {
        EntityManager em = emc.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        T t = findById(ID,cls);

        em.remove(em.contains(t) ? t : em.merge(t));

        et.commit();

        em.close();
    }
}
