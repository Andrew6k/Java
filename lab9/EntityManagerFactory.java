package JPA;

import com.company.Database;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityManagerFactory {
    private static EntityManagerFactory emf;
    public EntityManager em;
    private EntityManagerFactory(){
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("lab9");
        EntityManager em = emf.createEntityManager();
    }

    private EntityManager createEntityManager() {

    }
    public static EntityManagerFactory EntityManagerFactory() {
        if(emf==null)
        {
            emf=new EntityManagerFactory();
        }
        return emf;
    }

    public EntityManager getEntityManager() {
        return em ;
    }
}
