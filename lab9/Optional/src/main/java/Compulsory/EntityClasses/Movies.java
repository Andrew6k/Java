package Compulsory.EntityClasses;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "movies")
@NamedQueries({
        @NamedQuery(name = "Movies.findByName",
                    query = "SELECT m FROM Movies m where m.title LIKE :title")
})
public class Movies implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "release_date")
    private String release_date;

    @Column(name = "duration")
    private int duration;

    @Column(name = "score")
    private int score;

    //Constructor
    public Movies() {}

    //Setters and getters
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getRelease_date() {
        return release_date;
    }
    public int getDuration() {
        return duration;
    }
    public int getScore() {
        return score;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ID = " + id +
                "\nTitle = " + title +
                "\nRelease date = " + release_date.substring(0, 10) +
                "\nDuration = " + duration + " minutes" +
                "\nScore = " + score +
                "\n";
    }
}
