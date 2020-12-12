package aud8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

class Name {
    String name;
    int count;

    public Name(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public static Name createName(String line){
        String[] parts = line.split("\\s+");
        return new Name(parts[0], Integer.parseInt(parts[1]));
    }

}

public class Names {

    public static Map<String, Integer> createMapFromFile(String filename){
        Map<String, Integer> result = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            reader.lines()
                    .map(Name::createName)
                    .forEach(name -> result.put(name.name, name.count));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        Map<String, Integer> boys = createMapFromFile("C:\\Users\\Jordan\\IdeaProjects\\Napredno Programiranje\\NPAud\\src\\aud8\\boysnames.txt");
        Map<String, Integer> girls = createMapFromFile("C:\\Users\\Jordan\\IdeaProjects\\Napredno Programiranje\\NPAud\\src\\aud8\\girlsnames.txt");

        for(String boyName : boys.keySet()){
            if(girls.containsKey(boyName))
                System.out.println(boyName + "-->" + (boys.get(boyName) + girls.get(boyName)));
        }
    }
}
