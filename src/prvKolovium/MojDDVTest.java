//package prvKolovium;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//class AmountNotAllowedException extends Exception{
//    int sumOfItems;
//
//    public AmountNotAllowedException(int sumOfItems) {
//        this.sumOfItems = sumOfItems;
//    }
//
//    @Override
//    public String getMessage() {
//        return String.format("Receipt with amount %d is not allowed to be scanned", sumOfItems);
//    }
//}
//
//abstract class Item{
//      int price;
//
//    public Item( int price) {
//        this.price = price;
//    }
//
//    public abstract double getTaxReturn();
//
//    public static Item createItem(char type, int price){
//        switch (type){
//            case 'A': return ItemA(price);
//        }
//    }
//}
//
//class ItemA extends Item{
//
//
//    public ItemA(int price) {
//        super(price);
//    }
//
//    @Override
//    public double getTaxReturn() {
//        return price*0.18;
//    }
//}
//
//class ItemB extends Item{
//
//
//    public ItemB(int price) {
//        super(price);
//    }
//
//    @Override
//    public double getTaxReturn() {
//        return price*0.05;
//    }
//}
//class ItemC extends Item{
//
//
//    public ItemC(int price) {
//        super(price);
//    }
//
//    @Override
//    public double getTaxReturn() {
//        return price*0.18;
//    }
//}
//class Receipt{
//    String id;
//    List<Item> itemList;
//
//    public Receipt(String id, List<Item> itemList) {
//        this.id = id;
//        this.itemList = itemList;
//    }
//
//    public int itemsPriceSum(){
//        return itemList.stream().mapToInt(i->i.price).sum();
//    }
//
//    public double taxReturnSum(){
//        return itemList.stream().mapToDouble(Item::getTaxReturn).sum();
//    }
//
//
//    public static Receipt createReceipt(String line) throws AmountNotAllowedException {
//        String[] parts = line.split("\\s+");
//        String id = parts[0];
//        List<Item> items = new ArrayList<>();
//        for(int i=1;i<parts.length;i+=2){
//            Integer price = Integer.parseInt(parts[i]);
//            char type = parts[i+1].charAt(0);
//            items.add(new Item(type,price));
//        }
//        Receipt receipt = new Receipt(id,items);
//        if(receipt.itemsPriceSum()>30000) throw new AmountNotAllowedException(receipt.itemsPriceSum());
//        return receipt;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %d %.2f", id, itemsPriceSum(), taxReturnSum());
//    }
//}
//
//class MojDDV{
//    List<Receipt> receipts;
//
//    public MojDDV() {
//        receipts = new ArrayList<>();
//    }
//
//    public void readRecords(InputStream in) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//                String line;
//        while ((line = br.readLine()) != null) {
//            try {
//                receipts.add(Receipt.createReceipt(line));
//            } catch (AmountNotAllowedException e) {
//                System.out.println(e.getMessage());
//            }
//        }
////        receipts = br.lines().map(line-> {
////            try {
////                return Receipt.createReceipt(line);
////            } catch (AmountNotAllowedException e) {
////                e.printStackTrace();
////                return null;
////            }
////        }).filter(Objects::nonNull)
////                .collect(Collectors.toList());
//    }
//
//    public void printTaxReturns(OutputStream out){
//        PrintWriter printWriter = new PrintWriter(out);
//        for(Receipt r: receipts) printWriter.println(r.toString());
//        printWriter.flush();
//    }
//}
//
//public class MojDDVTest {
//
//    public static void main(String[] args) throws IOException {
//
//        MojDDV mojDDV = new MojDDV();
//
//        System.out.println("===READING RECORDS FROM INPUT STREAM===");
//        mojDDV.readRecords(System.in);
//
//        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
//        mojDDV.printTaxReturns(System.out);
//
//    }
//}