package kolokvium1;

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

class F1Race {

    List<Driver> drivers;

    public F1Race() {
        this.drivers = new ArrayList<>();
    }

    public void readResults(InputStream in) {
        Scanner sc = new Scanner(in);
        while(sc.hasNextLine()){
            String linija = sc.nextLine();
            drivers.add(new Driver(linija));
        }

//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//        drivers = br.lines().map(Driver::new).sorted(Comparator.comparing(Driver::bestLap)).collect(Collectors.toList());
    }

    public void printSorted(OutputStream out) {
        this.drivers = drivers.stream().sorted(Comparator.comparing(Driver::bestLap)).collect(Collectors.toList());
        PrintWriter pw = new PrintWriter(out);
        int i=1;
        for (Driver driver : drivers){
            pw.println(String.format("%d. %s", i++, driver.toString()));
        }
        pw.flush();
    }


    // vashiot kod ovde

}

class Driver {
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

    public String bestLap(){
        String bestLap = lap1;
        if (bestLap.compareTo(lap2) > 0) bestLap = lap2;
        if (bestLap.compareTo(lap3) > 0) bestLap = lap3;
        return bestLap;
    }

    @Override
    public String toString() {
        return String.format("%-10s%10s", name, bestLap());
    }
}