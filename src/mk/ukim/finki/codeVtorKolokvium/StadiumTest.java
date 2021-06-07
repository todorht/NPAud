//package mk.ukim.finki.codeVtorKolokvium;
//
//import java.util.*;
//
//public class StaduimTest {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        scanner.nextLine();
//        String[] sectorNames = new String[n];
//        int[] sectorSizes = new int[n];
//        String name = scanner.nextLine();
//        for (int i = 0; i < n; ++i) {
//            String line = scanner.nextLine();
//            String[] parts = line.split(";");
//            sectorNames[i] = parts[0];
//            sectorSizes[i] = Integer.parseInt(parts[1]);
//        }
//        Stadium stadium = new Stadium(name);
//        stadium.createSectors(sectorNames, sectorSizes);
//        n = scanner.nextInt();
//        scanner.nextLine();
//        for (int i = 0; i < n; ++i) {
//            String line = scanner.nextLine();
//            String[] parts = line.split(";");
//            try {
//                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
//                        Integer.parseInt(parts[2]));
//            } catch (SeatNotAllowedException e) {
//                System.out.println("SeatNotAllowedException");
//            } catch (SeatTakenException e) {
//                System.out.println("SeatTakenException");
//            }
//        }
//        stadium.showSectors();
//    }
//}
//
//class Sector{
//    String code;
//    int seats;
//    int freeSeats;
//    int type;
//
//    public Sector(String code, int seats) {
//        this.code = code;
//        this.seats = seats;
//        this.freeSeats = seats;
//        this.type = 0;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public int getSeats() {
//        return seats;
//    }
//
//    public void setSeats(int seats) {
//        this.seats = seats;
//    }
//
//    public int getFreeSeats() {
//        return freeSeats;
//    }
//
//    public void setFreeSeats(int freeSeats) {
//        this.freeSeats = freeSeats;
//    }
//
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//}
//
//
//class Stadium{
//    String name;
//    Map<String,Sector> sectors;
//
//    public Stadium(String name) {
//        this.name = name;
//        sectors = new TreeMap<>();
//    }
//
//    public void createSectors(String[] sectorNames, int[] sectorSizes) {
//        for(int i =0;i<sectorNames.length;i++){
//            sectors.put(sectorNames[i],new Sector(sectorNames[i],sectorSizes[i]));
//        }
//    }
//
//    public void buyTicket(String sectorName, int seat, int type){
//
//    }
//}