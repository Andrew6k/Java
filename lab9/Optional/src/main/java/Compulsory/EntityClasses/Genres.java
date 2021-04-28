package Compulsory.EntityClasses;
import javax.persistence.*;

@Entity
@Table(name = "genres", schema = "public")
public class Genres {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "genres_id_seq")
    @GeneratedValue(generator = "sequence")
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME")
    private String name;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

