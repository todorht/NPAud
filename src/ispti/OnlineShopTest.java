////package ispti;
//
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//
//
//enum COMPARATOR_TYPE {
//    NEWEST_FIRST,
//    OLDEST_FIRST,
//    LOWEST_PRICE_FIRST,
//    HIGHEST_PRICE_FIRST,
//    MOST_SOLD_FIRST,
//    LEAST_SOLD_FIRST
//}
//
//class ProductNotFoundException extends Exception {
//    ProductNotFoundException(String id) {
//        super(String.format("Product with id %s does not exist in the online shop!",id));
//    }
//}
//
//
//class Product {
//    private String category;
//    private String id;
//    private String name;
//    private LocalDateTime createdAt;
//    private double price;
//    private int sold;
//
//    public Product(String category, String id, String name, LocalDateTime createdAt, double price) {
//        this.category = category;
//        this.id = id;
//        this.name = name;
//        this.createdAt = createdAt;
//        this.price = price;
//        this.sold = 0;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Product product = (Product) o;
//        return Double.compare(product.price, price) == 0 && sold == product.sold && Objects.equals(category, product.category) && Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(createdAt, product.createdAt);
//    }
//
//    @Override
//    public String toString() {
//        return "Product{" +
//                "id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                ", createdAt=" + createdAt +
//                ", price=" + price +
//                ", quantitySold=" + sold +
//                '}';
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public int getSold() {
//        return sold;
//    }
//
//    public void setSold(int sold) {
//        this.sold = sold;
//    }
//}
//
//
//class OnlineShop {
//    private Map<String, Product> productMap;
//    private Map<String, List<Product>> productsByCategory;
//
//    OnlineShop() {
//        this.productMap = new HashMap<>();
//        this.productsByCategory = new HashMap<>();
//    }
//
//    void addProduct(String category, String id, String name, LocalDateTime createdAt, double price){
//        Product product = new Product(category, id, name, createdAt, price);
//        this.productMap.putIfAbsent(id, product);
//        if(!this.productsByCategory.containsKey(category)){
//           this.productsByCategory.put(category, new ArrayList<>());
//        }
//        this.productsByCategory.get(category).add(product);
//    }
//
//    double buyProduct(String id, int quantity) throws ProductNotFoundException{
//        if(!this.productMap.containsKey(id))
//            throw new ProductNotFoundException(id);
//        Product product = this.productMap.get(id);
//        product.setSold(product.getSold()+quantity);
//        return this.productMap.get(id).getPrice()*quantity;
//        //return 0.0;
//    }
//
//    static List<Product> sort(List<Product> productList, COMPARATOR_TYPE comparatorType){
//        switch (comparatorType){
//            case NEWEST_FIRST :
//                return productList.stream().sorted(Comparator.comparing(Product::getCreatedAt).reversed()).collect(Collectors.toList());
//            case OLDEST_FIRST :
//                return productList.stream().sorted(Comparator.comparing(Product::getCreatedAt)).collect(Collectors.toList());
//            case LOWEST_PRICE_FIRST :
//                return productList.stream().sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList());
//            case HIGHEST_PRICE_FIRST :
//                return productList.stream().sorted(Comparator.comparing(Product::getPrice).reversed()).collect(Collectors.toList());
//            case MOST_SOLD_FIRST :
//                return productList.stream().sorted(Comparator.comparing(Product::getSold).reversed()).collect(Collectors.toList());
//            case LEAST_SOLD_FIRST :
//                return productList.stream().sorted(Comparator.comparing(Product::getSold)).collect(Collectors.toList());
//            default :
//                return productList.stream().sorted(Comparator.comparing(Product::getCreatedAt)).collect(Collectors.toList());
//        }
//    }
//
//    List<List<Product>> listProducts(String category, COMPARATOR_TYPE comparatorType, int pageSize) {
//        List<List<Product>> result = new ArrayList<>();
//        List<Product> productList;
//        if(category!=null) productList = this.productsByCategory.get(category);
//        else productList = new ArrayList<>(this.productMap.values());
//        productList = sort(productList, comparatorType);
//
//        if(pageSize > productList.size())
//            pageSize = productList.size();
//
//        for(int i = 0; i< productList.size();i+=pageSize){
//            List<Product> subList = new ArrayList<>();
//            for(int j = i; j < i +pageSize;j++){
//                if(j== productList.size())
//                    break;
//                subList.add(productList.get(j));
//            }
//            result.add(subList);
//        }
//        return result;
//    }
//
//}
//
//public class OnlineShopTest {
//
//    public static void main(String[] args) {
//        OnlineShop onlineShop = new OnlineShop();
//        double totalAmount = 0.0;
//        Scanner sc = new Scanner(System.in);
//        String line;
//        while (sc.hasNextLine()) {
//            line = sc.nextLine();
//            String[] parts = line.split("\\s+");
//            if (parts[0].equalsIgnoreCase("addproduct")) {
//                String category = parts[1];
//                String id = parts[2];
//                String name = parts[3];
//                LocalDateTime createdAt = LocalDateTime.parse(parts[4]);
//                double price = Double.parseDouble(parts[5]);
//                onlineShop.addProduct(category, id, name, createdAt, price);
//            } else if (parts[0].equalsIgnoreCase("buyproduct")) {
//                String id = parts[1];
//                int quantity = Integer.parseInt(parts[2]);
//                try {
//                    totalAmount += onlineShop.buyProduct(id, quantity);
//                } catch (ProductNotFoundException e) {
//                    System.out.println(e.getMessage());
//                }
//            } else {
//                String category = parts[1];
//                if (category.equalsIgnoreCase("null"))
//                    category=null;
//                String comparatorString = parts[2];
//                int pageSize = Integer.parseInt(parts[3]);
//                COMPARATOR_TYPE comparatorType = COMPARATOR_TYPE.valueOf(comparatorString);
//                printPages(onlineShop.listProducts(category, comparatorType, pageSize));
//            }
//        }
//        System.out.println("Total revenue of the online shop is: " + totalAmount);
//
//    }
//
//    private static void printPages(List<List<Product>> listProducts) {
//        for (int i = 0; i < listProducts.size(); i++) {
//            System.out.println("PAGE " + (i + 1));
//            listProducts.get(i).forEach(System.out::println);
//        }
//    }
//}
//
