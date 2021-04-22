package JPA;

import java.io.Serializable;

@Entity
@Table(name = "ACTORS")
@NamedQueries({
        @NamedQuery(name = "Actor.findByName",
                query = "SELECT a FROM Product a WHERE a.name=:name")})
public class ActorJPA implements Serializable {
    @Id
    @SequenceGenerator(name = "sequence",
            sequenceName = "persons_id_seq")
    @GeneratedValue(generator = "sequence")
    @Column(name = "PERSON_ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name="BIRTH_DATE")
    private String birth_date;
    @Column(name="HEIGHT")
    private Integer height;
    @JoinColumn(name = "MOVIE_ID")
    @ManyToOne
    private MovieJPA movie;
    public ActorJPA(String s){
        name=s;
    }
    public String getName() {
        return name;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getId() {
        return id;
    }

    public MovieJPA getMovie() {
        return movie;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setMovie(MovieJPA movie) {
        this.movie = movie;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }
}