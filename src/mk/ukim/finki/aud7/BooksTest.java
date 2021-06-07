package mk.ukim.finki.aud7;

import java.util.*;
import java.util.stream.Collectors;

class Book {

    String title;
    String category;
    float price;
    //VTOR NACHIN
    static Comparator<Book> byNameAndPrice = (l,r)->{
        int res = l.title.compareToIgnoreCase(r.title);
        if(res==0) return Float.compare(l.price,r.price);
        else return res;
    };
    static Comparator<Book> byPrice = (l,r)-> {
        int res = Float.compare(l.price, r.price);
        if(res==0) return l.title.compareToIgnoreCase(r.title);
        else return res;
    };

    //TRET NACHIN
    static Comparator<Book> byNameAndPrice2 = Comparator.comparing(Book::getTitle).thenComparing(Book::getPrice);
    static Comparator<Book> byPrice2 = Comparator.comparing(Book::getPrice).thenComparing(Book::getTitle);

    public Book(String title, String category, float price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

//    @Override  //so implement Comperable<Book>  PRV NACHIN
//    public int compareTo(Book o) {
//        int res = this.title.compareTo(o.title);
//        if(res==0) return Float.compare(this.price,o.price);
//        else return res;
//    }

    @Override
    public String toString() {
        return title + " (" + category+ ") " + String.format("%.2f", price) ;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }
}

class BookCollection{
    List<Book> books;

    public BookCollection() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book){
        this.books.add(book);
    }

    public void printByCategory(String category){
        this.books.stream().filter(book->book.category.equalsIgnoreCase(category))
                .sorted(Book.byNameAndPrice2)
                .forEach(System.out::println);
    }

    public List<Book> getCheapestN(int n){
       return this.books.stream().sorted(Comparator.comparing(Book::getPrice).thenComparing(Book::getTitle)) // kodot od comparatorot moze diektno vo sort
               .limit(n).collect(Collectors.toList());
    }
}

public class BooksTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          BookCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}
