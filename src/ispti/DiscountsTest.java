//package ispti;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}

class Product{
    int price;
    int discountPrice;

    public Product(String part) {
        String[] parts = part.split(":");
        this.price = Integer.parseInt(parts[1]);
        this.discountPrice = Integer.parseInt(parts[0]);
    }

    double discountPercent(){
        return ((price-discountPrice)/(price*1.0))*100.0;
    }

    int discount(){
        return this.price-this.discountPrice;
    }
}

class Store{
    String storeName;
    List<Product> productList;


    public Store(String storeName, List<Product> productList) {
        this.storeName = storeName;
        this.productList = productList.stream().sorted(Comparator.comparing(Product::discountPercent).reversed()).collect(Collectors.toList());
    }

    public double getAverageDiscount() {
        double sum = this.productList.stream().mapToDouble(Product::discountPercent).sum();
        return sum/this.productList.size();
    }

    public int getSumDiscount(){
        return this.productList.stream().mapToInt(Product::discount).sum();
    }

    public String getStoreName() {
        return storeName;
    }

    @Override
    public String toString() {
        return String.format("%s\nAverage discount: %.1f%%\nTotal discount: %d\n%s",
                this.storeName,
                this.getAverageDiscount(),
                this.getSumDiscount(),
                this.productList.stream().map(product -> String.format("%.0f%% %d/%d",product.discountPercent(),
                        product.discountPrice,
                        product.price)).collect(Collectors.joining("\n")));
    }
}

class Discounts{

    List<Store> stores;

    public Discounts() {
        this.stores = new ArrayList<>();
    }

    int readStores(InputStream in) {
        Scanner sc = new Scanner(in);
        while (sc.hasNextLine()){
            String[] parts = sc.nextLine().split("\\s+");
            List<Product> productList = new ArrayList<>();
            for(int i = 1; i<parts.length;i++){
                productList.add(new Product(parts[i]));
            }
            this.stores.add(new Store(parts[0],productList));
        }
        return this.stores.size();
    }

    public List<Store> byAverageDiscount() {
        return this.stores.stream()
                .sorted(Comparator.comparing(Store::getAverageDiscount).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }


    public List<Store> byTotalDiscount() {
        return this.stores.stream()
                .sorted(Comparator.comparing(Store::getSumDiscount).thenComparing(Store::getStoreName))
                .limit(3)
                .collect(Collectors.toList());
    }
}