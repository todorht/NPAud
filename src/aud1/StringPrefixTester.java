package aud1;

import java.util.stream.IntStream;

public class StringPrefixTester {

    public static boolean isPrefix (String str1, String str2){
        if (str1.length()>str2.length()) return false;
        else if (str1.equals(str2)) return true;
        else {
//            for(int i=0;i<str1.length();i++) {
//                if (str1.charAt(i) != str2.charAt(i)) return false;
//            }
//            return true;
            return IntStream.range(0,str1.length())
                    .allMatch(i -> str1.charAt(i) == str2.charAt(i));
        }
    }

    public static void main(String[] args) {
        String s1 = "Jordan";
        String s2 = "joRdan";
        String s3 = "Jor";
        String s4 = "Jordan";


        System.out.println(isPrefix(s1,s4));
        System.out.println(isPrefix(s3,s1));
        System.out.println(isPrefix(s2,s1));
    }
}
