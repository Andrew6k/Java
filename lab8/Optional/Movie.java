package com.company;

import java.sql.Date;
import java.time.LocalDate;

public class Movie {
    private Integer id;
    private String title;
    private String releaseDate;
    private Integer duration;
    private Integer score;

    public Movie() {
        this.id = 0;
        this.title = "";
        this.releaseDate = "20-11-2020";
        this.duration = 0;
        this.score = 0;
    }

    public Movie(Integer id, String title, String releaseDate, Integer duration, Integer score) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", score=" + score +
                '}';
    }
}
