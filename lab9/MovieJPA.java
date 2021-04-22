package JPA;

import java.io.Serializable;

@Entity
@Table(name = "MOVIES")
public class MovieJPA implements Serializable {
    @Id
    @SequenceGenerator(name = "sequence",
            sequenceName = "persons_id_seq")
    @GeneratedValue(generator = "sequence")
    @Column(name = "MOVIE_ID")
    private Integer id;
    @Column(name = "TITLE")
    private String title;
    @Column(name="RELEASE_DATE")
    private String release_date;
    @Column(name="SCORE")
    private Integer score;

    public MovieJPA(String title) {
        this.title = title;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setMovie(MovieJPA movie) {
        this.movie = movie;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Integer getScore() {
        return score;
    }

    public MovieJPA getMovie() {
        return movie;
    }

    @JoinColumn(name = "ACTOR_ID")
    @ManyToOne
    private MovieJPA movie;
}
