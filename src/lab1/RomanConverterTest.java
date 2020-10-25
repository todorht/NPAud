package lab1;
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

        String[] romanNumbers = {"I", "IV", "V", "VI", "IX",
                "X", "XL", "L", "LX", "XC",
                "C", "CD", "D", "DC","CM", "M"};
        int[] divisors = {1, 4, 5, 6, 9,
                10, 40, 50, 60, 90,
                100, 400, 500, 600, 900, 1000};

        StringBuilder rNumber = new StringBuilder();

        findRoman(n, romanNumbers, divisors, rNumber);


        return rNumber.toString();
    }

    private static void findRoman(int n, String[] romanNumbers, int[] divisors, StringBuilder rNumber) {
        int i = romanNumbers.length-1;

        while(n > 0){
            if(n - divisors[i] >= 0){
                rNumber.append(romanNumbers[i]);
                n -= divisors[i];
            }
            else
                i--;
        }
    }

}
