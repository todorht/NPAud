//package mk.ukim.finki.test;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class Driver{
    String name;
    String lap1;
    String lap2;
    String lap3;

    public Driver(String line) {
        String[] parts = line.split("\\s+");
        this.name = parts[0];
        this.lap1 = parts[1];
        this.lap2 = parts[2];
        this.lap3 = parts[3];
    }
    //Massa 1:57:563 1:55:187 1:55:634
    String bestLap(){
        String bestLap = lap1;
        if(bestLap.compareTo(lap2)>0) bestLap = lap2;
        if(bestLap.compareTo(lap3)>0) bestLap = lap3;
        return bestLap;
    }

    @Override
    public String toString() {
        return String.format("%-10s %10s",name,bestLap());
    }
}

class F1Race {
    // vashiot kod ovde
    List<Driver> drivers;

    public F1Race() {
        this.drivers = new ArrayList<>();
    }

    void readResults(InputStream in){
        Scanner sc = new Scanner(in);
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            drivers.add(new Driver(line));
        }
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//        drivers = br.lines().map(Driver::new).sorted(Comparator.comparing(Driver::bestLap)).collect(Collectors.toList());
    }

    void printSorted(OutputStream out){
        this.drivers = drivers.stream().sorted(Comparator.comparing(Driver::bestLap)).collect(Collectors.toList());
        PrintWriter pw = new PrintWriter(out);
        drivers.forEach((driver)->{
            pw.println(driver.toString());
        });
        pw.flush();
    }
}