package Compulsory;

import Compulsory.EntityClasses.Charts;
import Compulsory.EntityClasses.Genres;
import Compulsory.EntityClasses.Movies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {
        MoviesRepository mr = new MoviesRepository();
        Movies movie = new Movies();

        movie.setId(10);
        movie.setTitle("Forrest Gump");
        movie.setRelease_date("06-06-1994");
        movie.setDuration(142);
        movie.setScore(9);

        //Inseram filmul in BD
        mr.create(movie);

        //Afisam datele despre filmul "Pulp Fiction" cu ID-ul 1
        System.out.println("Filmul cu ID-ul 1 este:");
        System.out.println(mr.findById(1));


        List<Movies> lotrMovies = mr.findByName("LOTR");
        /*
        for (Movies m : lotrMovies) {
            System.out.println(m);
        }
        */

        mr.remove(10);
        System.out.println();
        AbstractRepository<Genres> gr=new AbstractRepository<>();
        //Genres genre=GenreRepository.get_by_id(112);
        Charts chart1 =new Charts("LOTR Movies",new SimpleDateFormat("dd.MM.yyyy").parse("29.04.2021"));
        chart1.setMoviesList(lotrMovies);
        chart1.printMoviesSortedScore();

        List<Movies> godfatherMovies=mr.findByName("Godfather");
        Charts chart2= new Charts("Godfather Movies",new SimpleDateFormat("dd.MM.yyyy").parse("20.03.2021"));
        chart2.setMoviesList(godfatherMovies);
        chart2.printMoviesSortedDuration();
    }
}