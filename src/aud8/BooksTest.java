package aud8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
        TreeSet<String> categories = new TreeSet<>();
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

// Вашиот код овде

class Book {
    String title;
    String category;
    float price;
//    static Comparator<Book> compareByTitleandPrice = (l, r) -> {
//        int res = l.title.compareToIgnoreCase(r.title);
//        if(res==0)
//            return Float.compare(l.price, r.price);
//        else
//            return res;
//    };

    static Comparator<Book> compareByTitleandPrice = Comparator.comparing(Book::getTitle).thenComparing(Book::getPrice);

//    static Comparator<Book> compareByPrice = (l, r) -> {
//        int res = Float.compare(l.price, r.price);
//        return (res == 0) ? l.title.compareToIgnoreCase(r.title) : res;
//    };

    static Comparator<Book> compareByPrice = Comparator.comparing(Book::getPrice).thenComparing(Book::getTitle);

    public Book(String title, String category, float price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        //A Brief History of Time (A) 25.66
        return String.format("%s (%s) %.2f", title, category, price);
    }

    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }
}

class BookCollection {
    List<Book> books;

    public BookCollection() {
        this.books = new ArrayList<>();
    }


    public void printByCategory(String category) {
        books.stream()
                .filter(book -> book.category.equalsIgnoreCase(category))
                .sorted(Book.compareByTitleandPrice)
                .forEach(System.out::println);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getCheapestN(int n) {
        return books.stream()
                .sorted(Book.compareByPrice)
                .limit(n)
                .collect(Collectors.toList());
    }
}