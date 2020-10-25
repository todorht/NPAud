package mk.ukim.finki.labs.codeVoved;

import java.util.Scanner;
import java.util.stream.IntStream;

public class RomanConverterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        IntStream.range(0, n)
                .forEach(x -> System.out.println(RomanConverter.toRoman(scanner.nextInt())));
        scanner.close();
    }
}


class RomanConverter {
    /**
     * Roman to decimal converter
     *
     * @param n number in decimal format
     * @return string representation of the number in Roman numeral
     */

    public static String toRoman(int n) {
        // your solution here
        String[][] digits = {
                {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},
                {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
                {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},
                {"", "M", "MM", "MMM", "MMMM", "MMMMM", "MMMMMM", "MMMMMMM", "MMMMMMMM", "MMMMMMMMM"}
        };

        StringBuilder rimski = new StringBuilder();

        int i = 0;
        while (n>0){
            int j = n%10;
            rimski.insert(0,digits[i][j]);  // .insert mi go ovozmozuva StringBuilder
            i++;
            n/=10;
        }

        return rimski.toString();
    }

}
