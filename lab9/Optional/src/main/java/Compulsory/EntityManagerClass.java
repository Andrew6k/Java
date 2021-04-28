package Compulsory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerClass {
    private static EntityManagerClass entityManager = null;
    private static EntityManagerFactory factory = Persistence
            .createEntityManagerFactory("Laboratorul9");

    //Constructor
    private EntityManagerClass() {}
    //Get instance method
    public static EntityManagerClass getInstance() {
        if (entityManager == null)
            entityManager = new EntityManagerClass();
        return entityManager;
    }
    //Create entity manager
    public EntityManager createEntityManager() {
        return factory.createEntityManager();
    }
}
