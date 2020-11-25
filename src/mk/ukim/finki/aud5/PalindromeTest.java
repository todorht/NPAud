package mk.ukim.finki.aud5;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PalindromeTest {

    public static List<String> readWords(InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        return br.lines().map(word->word.toLowerCase())
                .filter(word -> isPalindrome(word))
                .collect(Collectors.toList());
    }

    public static boolean isPalindrome(String word){
        String reverseWord = new StringBuilder().append(word).reverse().toString();
        return word.equals(reverseWord);
    }

    public static boolean isPalindromeStream(String word){
       return IntStream.range(0,word.length())
                .allMatch(i->word.charAt(i)==word.length()-i-1);
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\FINKI\\Zimski\\NP\\NPAud\\src\\mk\\ukim\\finki\\pearson");
        List<String> words = readWords(new FileInputStream(file));
        String maxLengthPalindrome = words.stream().max(Comparator.comparingInt(w -> w.length())).get();

        System.out.println(maxLengthPalindrome);
    }

}
