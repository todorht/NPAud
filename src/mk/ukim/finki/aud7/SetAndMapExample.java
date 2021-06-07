package mk.ukim.finki.aud7;

import java.util.*;

public class SetAndMapExample {

    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>();
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        TreeSet<String> treeSet = new TreeSet<>(Comparator.comparing(String::length));

        hashSet.add("123456");
        hashSet.add("Stefan");
        hashSet.add("Lazo");
        hashSet.add("Cacko");

        linkedHashSet.add("123456");
        linkedHashSet.add("Stefan");
        linkedHashSet.add("Lazo");
        linkedHashSet.add("Cacko");

        treeSet.add("1234567");
        treeSet.add("Stefan");
        treeSet.add("Lazo");
        treeSet.add("Cacko");

        System.out.println(hashSet);
        System.out.println(linkedHashSet);
        System.out.println(treeSet);

        HashMap<String, String> hashMap = new HashMap<>();
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>();
        TreeMap<String, String> treeMap = new TreeMap<>();

        hashMap.put("tode","sssda");
        hashMap.put("joco","213sda");
        hashMap.put("temen","sdsassda");
        hashMap.put("kich","sssssda");

        linkedHashMap.put("tode","sssda");
        linkedHashMap.put("joco","213sda");
        linkedHashMap.put("temen","sdsassda");
        linkedHashMap.put("kich","sssssda");

        treeMap.put("tode","sssda");
        treeMap.put("joco","213sda");
        treeMap.put("temen","sdsassda");
        treeMap.put("kich","sssssda");

        System.out.println(hashMap);
        System.out.println(linkedHashMap);
        System.out.println(treeMap);

    }
}
