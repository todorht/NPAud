package mk.ukim.finki.codeVtorKolokvium;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        names.findName(len, index);
        scanner.close();

    }
}

// vashiot kod ovde

class Name{
    String name;
    int value;

    public Name(String name) {
        this.name = name;
        this.value = 1;
    }

    private int getNumLetters(){
        char[] tmp = name.toLowerCase().toCharArray();
        Set<Character> letters = new HashSet<>();
        for(int i = 0;i<name.length();i++){
            letters.add(tmp[i]);
        }

        return letters.size();
    }

    @Override
    public String toString() {
        return String.format("%s (%d) %d", name,value, getNumLetters());
    }
}

class Names{
    TreeMap<String, Name> names;

    public Names() {
        this.names = new TreeMap<>();
    }


    public void addName(String name) {
        if(this.names.containsKey(name)){
            Name input = this.names.get(name);
            input.value+=1;
            this.names.put(name,input);
        }else {
            this.names.put(name,new Name(name));
        }
    }

    public void printN(int n) {
        names.values().stream().filter(name -> name.value>=n).forEachOrdered(System.out::println);
    }

    public void findName(int len, int index) {
        List<Name> nameList = names.values().stream().filter(name -> name.name.length()<len).collect(Collectors.toList());
        System.out.println(nameList.get(index % nameList.size()).name);
    }
}