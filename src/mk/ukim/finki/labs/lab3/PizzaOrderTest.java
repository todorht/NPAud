package mk.ukim.finki.labs.lab3;

import java.util.*;
import java.util.stream.IntStream;

class InvalidExtraTypeException extends Exception{}
class InvalidPizzaTypeException extends Exception{}
class ItemOutOfStockException extends Exception{
    public ItemOutOfStockException(Item item){
        super(String.format("%s out of stock!", item));
    }
}
class EmptyOrder extends Exception{}
class OrderLockedException extends Exception{}

interface Item{
    public int getPrice();
    public String getType();
}

class ExtraItem implements Item {

    private String type;

    public ExtraItem(String type1) throws InvalidExtraTypeException {
        if(type1.equals("Ketchup") || type1.equals("Coke")) this.type = type1;
        else throw new InvalidExtraTypeException();
    }

    @Override
    public int getPrice() {
        if(this.getType().equals("Ketchup")) return 3;
        else return 5;
    }

    @Override
    public String getType() {
        return type;
    }
}

class PizzaItem implements Item{

    private String type;

    public PizzaItem(String type1) throws InvalidPizzaTypeException {
        if(type1.equals("Standard") || type1.equals("Pepperoni") || type1.equals("Vegetarian")) this.type = type1;
        else throw new InvalidPizzaTypeException();
    }

    @Override
    public int getPrice() {
        if(getType().equals("Standard")) return 10;
        else if(getType().equals("Pepperoni")) return 12;
        else return 8;
    }

    @Override
    public String getType() {
        return type;
    }
}

class Order {
    private List<Item> items;
    private List<Integer> counts;
    private boolean isLocked;

    public Order() {
        this.items = new ArrayList<>();
        this.counts = new ArrayList<>();
        this.isLocked = false;
    }

    public void addItem(Item item, int count) throws ItemOutOfStockException, OrderLockedException {
        if(isLocked) throw new OrderLockedException();
        if(count>10) throw new ItemOutOfStockException(item);
        int index = IntStream.range(0,items.size()).filter(i->items.get(i).getType().equals(item.getType())).findFirst().orElse(-1);
        if(index==-1) {
            items.add(item);
            counts.add(count);
        }else{
            counts.set(index,count);
        }
    }

    public int getPrice(){
        int sum =0;
        for(int i =0;i<items.size();i++){
            sum+=(items.get(i).getPrice()*counts.get(i));
        }
        return sum;
    }

    public void displayOrder(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<items.size();i++){
            sb.append(String.format("%3d.%-15sx%2d%5d$\n",i+1,items.get(i).getType(),counts.get(i),
                                                        items.get(i).getPrice()*counts.get(i)));
        }
        sb.append(String.format("%-22s%5d$","Total:",this.getPrice()));
        System.out.println(sb.toString());
    }

    public void removeItem(int idx) throws OrderLockedException {
        if(isLocked) throw new OrderLockedException();
        try {
            items.remove(idx);
            counts.remove(idx);
        }catch (ArrayIndexOutOfBoundsException e){
            throw new ArrayIndexOutOfBoundsException(idx);
        }
    }

    public void lock() throws EmptyOrder {
        if(items.isEmpty()) throw new EmptyOrder();
        else this.isLocked=true;
    }
}

public class PizzaOrderTest {
    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}