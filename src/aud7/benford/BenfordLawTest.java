package aud7.benford;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class BenfordLawTest {
    public static String INPUT_FILE = "C:\\Users\\Jordan\\IdeaProjects\\Napredno Programiranje\\NPAud\\src\\library_books.txt";

    public static int[] counts(List<Integer> numbers){
        int[] result = new int[10];

        for(Integer number : numbers){
            int digit = firstDigit(number);
            result[digit]++;
        }
        return result;
    }

    public static int[] countsFunctional(List<Integer> numbers){
        return numbers.stream()
                .map(BenfordLawTest::firstDigit)
                .map(i -> {
                    int[] res = new int[10];
                    res[i]++;
                    return res;
                })
                .reduce(new int[10], (left, right) -> {
                    Arrays.setAll(left, i-> left[i] + right[i]);
                    return left;
                });
    }

    private static int firstDigit(Integer number) {
        while(number>=10){
            number /= 10;
        }
        return number;
    }

    public static void main(String[] args) {
        try {
            NumbersReader numbersReader = new LineNumbersReader();
            List<Integer> numbers = numbersReader.read(new FileInputStream(INPUT_FILE));
            CountVisualizer countVisualizer = new CountVisualizer(100);
            countVisualizer.visualize(System.out, counts(numbers));
            countVisualizer.visualize(System.out, countsFunctional(numbers));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
