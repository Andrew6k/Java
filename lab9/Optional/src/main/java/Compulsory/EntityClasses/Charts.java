package Compulsory.EntityClasses;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class Charts {


    public String name;
    public Date creation_date;
    public List<Movies> moviesList;

    public Charts(String name, Date creation_date) {
        this.name = name;
        this.creation_date = creation_date;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public List<Movies> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(List<Movies> moviesList) {
        this.moviesList = moviesList;
    }

    public void printMoviesSortedScore(){
        Collections.sort(moviesList,Comparator.comparing(Movies::getScore));
        for (Movies m : moviesList) {
            System.out.println(m);
            }
        //Collections.sort(moviesList, Collections.reverseOrder());
    }
    public void printMoviesSortedDuration(){
        Collections.sort(moviesList,Comparator.comparing(Movies::getDuration));
        for (Movies m : moviesList) {
            System.out.println(m);
        }
    }
}
