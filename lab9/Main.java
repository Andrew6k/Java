package JPA;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory(
                        "lab8", properties);
        EntityManager em = factory.createEntityManager();
        ActorJPA a1=new ActorJPA("Brad Pitt");
        MovieJPA m1=new MovieJPA("Se7en");
        em.persist(mvoie);
        em.getTransaction().commit();
        em.close();
        factory.close();
    }
}
