package aud8;

import java.util.*;

public class MapExample {

    private static void addToMap(Map<String, String> map1,
                                 Map<String, String> map2,
                                 Map<String, String> map3,
                                 Map<String, String> map4,
                                 String key,
                                 String value){
        map1.put(key, value);
        map2.put(key, value);
        map3.put(key, value);
        map4.put(key, value);
    }

    public static void main(String[] args) {
        // K = username, V = password
        //1. HashMap - ne zadrzhuva redolsed, hash() na key
        Map<String, String> hashMap = new HashMap<>();

        //2. LinkedHashMap - go zadrzhuva redosledot
        Map<String, String> linkedHashMap = new LinkedHashMap<>();

        //3. TreeMap - sortira spored compareTo na key ili spored Comparator
        Map<String, String> treeMap = new TreeMap<>();

        Map<String, String> treeMap1 = new TreeMap<>(Comparator.comparing(String::length));

        addToMap(hashMap, linkedHashMap, treeMap, treeMap1,"ZZZ", "finki321");
        addToMap(hashMap, linkedHashMap, treeMap, treeMap1, "Jordan", "1234253");
        addToMap(hashMap, linkedHashMap, treeMap, treeMap1, "Stefan", "12353423");
        addToMap(hashMap, linkedHashMap, treeMap, treeMap1,"Napredno programiranje", "password");
        addToMap(hashMap, linkedHashMap, treeMap, treeMap1,"1234534", "finki");
        addToMap(hashMap, linkedHashMap, treeMap, treeMap1,"XYZ", "finki123");

        System.out.println("HASH MAP");
        System.out.println(hashMap);
        System.out.println("LINKED HASH MAP");
        System.out.println(linkedHashMap);
        System.out.println("TREE MAP");
        System.out.println(treeMap);
        System.out.println("TREE MAP1");
        System.out.println(treeMap1);
    }
}
