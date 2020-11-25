package mk.ukim.finki.aud4.Chitanje;

import java.util.Scanner;

public class CalculatorTest {

    public static char firstChar(String line){
        return Character.toLowerCase(line.charAt(0));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            Calculator calculator = new Calculator();
            System.out.println(calculator.init());
            while (true) {
                String line = scanner.nextLine();
                if (firstChar(line) == 'r') {
                    System.out.println(String.format("final result = %.2f", calculator.getResult()));
                    break;
                }
                String[] parts = line.split("\\s+"); //interesno (\\s+) - edno ili povekje prazni mesta
                char operator = parts[0].charAt(0);
                double value = Double.parseDouble(parts[1]);
                try {
                    String result = calculator.execute(operator,value);
                    System.out.println(result);
                    System.out.println(calculator);
                } catch (UnknownOperatorException e){
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Y/N");
            String line = scanner.nextLine();
            char choice = firstChar(line);
            if(choice=='n') break;

        }
    }
}
