package mk.ukim.finki.aud6.benford;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class BenfordLawTest {

    public static String FILE_NAME = "E:\\faks\\Zimski\\NP\\NPAud\\izvor.txt";

    static int[] counts(List<Integer> numbers) {
        int[] result = new int[10];
        int digit = 0;
        for (Integer number : numbers) {
            digit = firstDigit(number);
            result[digit]++;
        }
        return result;
    }

    public static int[] countsFunctional(List<Integer> numbers){
        return numbers.stream().map(BenfordLawTest::firstDigit)
                .map(x->{
                    int[] result = new int[10];
                    result[x]++;
                    return result;
                }).reduce(new int[10], (left, right) -> {
                    Arrays.setAll(left, i ->left[i] + right[i]); //gi zima elementite od left i right na pozicija i,pa gi stava vo left na pozicija i
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
            CountVisualizer visualizer = new CountVisualizer(10); //na sekoj n:10 najdeni printa zvezda
            visualizer.visualizerShow(System.out, countsFunctional(numbers)); //niza od koj broj kolku pati se pojavuva
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }



    }
}


