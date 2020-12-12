package aud8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CountOccurence {
    public static int count(Collection<Collection<String>> c, String str){
        int count = 0;
        for (Collection<String> subc : c){
            for (String s : subc){
                if(s.equalsIgnoreCase(str))
                    ++count;
            }
        }
        return count;
    }

    public static int countFunctional1(Collection<Collection<String>> c, String str){
        return (int) c.stream()
                .flatMap(Collection::stream)
                .filter(s -> s.equalsIgnoreCase(str))
                .count();
    }

    public static int countFunctional2(Collection<Collection<String>> c, String str){
        return c.stream()
                .flatMapToInt(collection -> collection.stream()
                        .mapToInt(s -> s.equalsIgnoreCase(str) ? 1 : 0))
                .sum();

//        return c.stream()
//                .mapToInt(sub -> sub.stream().mapToInt(s -> s.equalsIgnoreCase(str) ? 1 :0).sum())
//                .sum();
    }

    public static void main(String[] args) {
        Collection<Collection<String>> collections = new ArrayList<>();

        List<String> list1 = List.of("1", "11", "123");
        List<String> list2 = List.of("11", "111", "123");
        List<String> list3 = List.of("1", "11", "113");

        collections.add(list1);
        collections.add(list2);
        collections.add(list3);

        System.out.println(count(collections, "1"));
        System.out.println(countFunctional1(collections, "1"));
        System.out.println(countFunctional2(collections, "1"));
    }
}
