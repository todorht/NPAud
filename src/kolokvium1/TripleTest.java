package kolokvium1;

import java.util.Scanner;

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.average());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.average());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.average());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
// vasiot kod ovde
// class Triple
class Triple<T extends Number> {
    T firstNumber;
    T secondNumber;
    T thirdNumber;

    public Triple(T firstNumber, T secondNumber, T thirdNumber) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.thirdNumber = thirdNumber;
    }

    double max(){
        Double max = firstNumber.doubleValue();
        if(max.compareTo(secondNumber.doubleValue()) < 0) max = secondNumber.doubleValue();
        if(max.compareTo(thirdNumber.doubleValue()) < 0) max = thirdNumber.doubleValue();
        return max;
    }

    double average(){
        Double sum = firstNumber.doubleValue() + secondNumber.doubleValue() + thirdNumber.doubleValue();
        return sum / 3.0;
    }

    void sort(){
        if(firstNumber.doubleValue() > secondNumber.doubleValue()){
            T tmp = secondNumber;
            secondNumber = firstNumber;
            firstNumber = tmp;
        }
        if(secondNumber.doubleValue() > thirdNumber.doubleValue()) {
            T tmp = thirdNumber;
            thirdNumber = secondNumber;
            secondNumber = tmp;
        }
        if(firstNumber.doubleValue() > secondNumber.doubleValue()) {
            T tmp = secondNumber;
            secondNumber = firstNumber;
            firstNumber = tmp;
        }
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f", firstNumber.doubleValue(), secondNumber.doubleValue(), thirdNumber.doubleValue());
    }
}


