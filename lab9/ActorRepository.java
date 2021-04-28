package JPA;

public class ActorRepository {
    private EntityManager em;

    public ActorRepository(EntityManager em) {
        this.em = em;
    }
    public ActorJPA getActorById(int id){
        return em.find(ActorJPA.class, id);
    }

    public ActorJPA getActorByName(String name){
        TypedQuery<ActorJPA> q = em.createQuery("SELECT a FROM ACTORS a WHERE a.name = :name", ActorJPA.class);
            q.setParameter("name", name);
            return q.getSingleResult();
    }

    public ActorJPA saveActor(ActorJPA b) {
        if (b.getId() == null) {
            em.persist(b);
        } else {
            b = em.merge(b);
        }
        return b;
    }
}
