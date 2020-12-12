package aud8;

import java.util.*;

public class SetExample {

    private static void addToSet(Set<String> set1, Set<String> set2, Set<String> set3, Set<String> set4, String text){
        set1.add(text);
        set2.add(text);
        set3.add(text);
        set4.add(text);
    }

    public static void main(String[] args) {
        //1. HashSet - ne go zapazuva redosledot, redosledot se utvrduva spored hash()
        Set<String> hashSet = new HashSet<>();

        //2. LinkedHashSet - go zapazuva redosledot
        Set<String> linkedHashSet = new LinkedHashSet<>();

        //3. TreeSet - gi sortira vrednostite leksikografski
        Set<String> treeSet = new TreeSet<>();

        //4. TreeSet - gi sortira stringovite spored dolzhinata
        Set<String> treeSet1 = new TreeSet<>(Comparator.comparingInt(String::length));

        addToSet(hashSet, linkedHashSet, treeSet, treeSet1,"ZZZ");
        addToSet(hashSet, linkedHashSet, treeSet, treeSet1, "Jordan");
        addToSet(hashSet, linkedHashSet, treeSet, treeSet1, "Stefan");
        addToSet(hashSet, linkedHashSet, treeSet, treeSet1,"Napredno programiranje");
        addToSet(hashSet, linkedHashSet, treeSet, treeSet1,"1234534");
        addToSet(hashSet, linkedHashSet, treeSet, treeSet1,"XYZ");

        System.out.println("HASH SET");
        System.out.println(hashSet);
        System.out.println("LINKED HASH SET");
        System.out.println(linkedHashSet);
        System.out.println("TREE SET");
        System.out.println(treeSet);
        System.out.println("TREE SET1");
        System.out.println(treeSet1);
    }
}
