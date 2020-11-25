package mk.ukim.finki.aud5;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Pearson implements Comparable<Pearson>{
    String name;
    int age;

    public Pearson(String inputLine) {
        String [] parts = inputLine.split("\\s+");
        this.name = parts[0];
        this.age = Integer.parseInt(parts[1]);
    }

    @Override
    public String toString() {
        return "Pearson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(Pearson o) {
        return Integer.compare(this.age,o.age);
    }
}

public class OldestPearsonTest {

    public static List<Pearson> readPearsonFrom(InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        return br.lines()
                .map(line -> new Pearson(line))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\FINKI\\Zimski\\NP\\NPAud\\src\\mk\\ukim\\finki\\pearson");

        List<Pearson> pearsons = readPearsonFrom(new FileInputStream(file));
        Pearson max = pearsons.stream()
                .max(Comparator.naturalOrder()).get();// .naturalOrder() -> povikuva compareTo() od soodvetnata klasata

//        Collections.sort(pearsons);
//        System.out.println(pearsons.get(pearsons.size()-1));

        System.out.println(max);
    }
}
