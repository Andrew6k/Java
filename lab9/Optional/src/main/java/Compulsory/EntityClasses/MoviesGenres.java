package Compulsory.EntityClasses;

import javax.persistence.*;

@Entity
@Table(name = "MOVIES_GENRES")
public class MoviesGenres {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "movies_id_seq")
    @GeneratedValue(generator = "sequence")
    @Column(name = "ID")
    private long id;

    @Column(name = "ID_MOVIE")
    private long idMovie;

    @Column(name = "ID_GENRE")
    private long idGenre;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(long idMovie) {
        this.idMovie = idMovie;
    }


    public long getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(long idGenre) {
        this.idGenre = idGenre;
    }

}

