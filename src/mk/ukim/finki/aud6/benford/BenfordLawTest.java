package mk.ukim.finki.aud6.benford;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class BenfordLawTest {

    public static String FILE_NAME = "E:\\FINKI\\Zimski\\NP\\NPAud\\src\\mk\\ukim\\finki\\output";

    static int[] counts(List<Integer> numbers) {
        int[] result = new int[10];
        int digit = 0;
        for (Integer number : numbers) {
            digit = firstDigit(number);
            result[digit]++;
        }
        return result;
    }

    public static  int[] countsFunctional(List<Integer> numbers){
        return numbers.stream().map(BenfordLawTest::firstDigit)
                .map(x->{
                    int[] result = new int[10];
                    result[x]++;
                    return result;
                }).reduce(new int[10], (left, right) -> {
                    Arrays.setAll(left, i ->left[i] + right[i]);
                    return left;
                });
    }

    static int firstDigit(int num){
        while (num>=10){
            num/=10;
        }
        return num;
    }

    public static void main(String[] args){
        LineNumbersReader numbersReader = new LineNumbersReader();
        try {
            List<Integer> numbers = numbersReader.read(new FileInputStream(FILE_NAME));
            CountVisualizer visualizer = new CountVisualizer(100);
            visualizer.visualizer(System.out, countsFunctional(numbers));
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }



    }
}


