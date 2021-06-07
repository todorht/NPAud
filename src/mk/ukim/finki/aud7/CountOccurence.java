package mk.ukim.finki.aud7;

import java.util.Collection;

public class CountOccurence {

//    public static int count(Collection<Collection<String>> c, String str){  //[[s1,s2,s3][s4,s5,s6]]
//        int counter =0;
//        for (Collection<String> subc : c){
//            for (String s:subc){
//                if(s.equalsIgnoreCase(str)) counter++;
//            }
//        }
//        return counter;
//    }

    public static int countWithStreams(Collection<Collection<String>> c, String str){

        return (int) c.stream().flatMap(sub->sub.stream())    //[[s1,s2,s3][s4,s5,s6]]->[s1,s2,s3,s4,s5,s6]
                .filter(s->s.equalsIgnoreCase(str)).count();

        //return c.stream().flatMapToInt(sub -> sub.stream().mapToInt(s->s.equalsIgnoreCase(str) ? 1 : 0))
        // .sum(); //ako e equals smesti 1 i sum na kraj

    }

    public static void main(String[] args) {

    }
}
