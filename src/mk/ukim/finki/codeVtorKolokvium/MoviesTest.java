package mk.ukim.finki.codeVtorKolokvium;

import java.util.*;
import java.util.stream.Collectors;

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

// vashiot kod ovde

class Movie implements Comparable<Movie> {

    String title;
    int[] ratings;

    public Movie(String title, int[] ratings) {
        this.title = title;
        this.ratings = ratings;
    }

    public double averageRating(){
        double sum = 0;
        for(int rate: this.ratings){
            sum+=rate;
        }
        return sum/this.ratings.length;
    }

    @Override
    public int compareTo(Movie o) {
        int rez = Double.compare(this.averageRating(),o.averageRating());
        return rez==0 ? this.title.compareTo(o.title) : rez;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings", this.title, this.averageRating(), this.ratings.length);
    }

    public String getTitle() {
        return title;
    }

    public int getNumRating(){
        return this.ratings.length;
    }
}

class MoviesList{

    Set<Movie> movies;

    public MoviesList() {
        this.movies = new TreeSet<>();
    }

    Comparator<Movie> comparator = Comparator.comparing(movie -> (movie.averageRating() * movie.ratings.length / max()));

    public void addMovie(String title, int[] ratings) {

        this.movies.add(new Movie(title, ratings));
    }

    public List<Movie> top10ByAvgRating() {
        return this.movies.stream()
                .sorted(Comparator.comparing(Movie::averageRating).reversed()
                        .thenComparing(Movie::getTitle))
                .collect(Collectors.toList()).subList(0, 10);
    }

    public int max(){
        return (int) movies.stream().mapToInt(movie -> movie.ratings.length).count();
    }
    
    public List<Movie> top10ByRatingCoef() {
        return this.movies.stream()
                .sorted(comparator.reversed().thenComparing(Movie::getTitle))
                .collect(Collectors.toList()).subList(0,10);
    }
}