//package mk.ukim.finki.test;

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
class Triple<T extends Number>{
    T first;
    T second;
    T third;

    public Triple(T first, T second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    double max(){
        Double max = first.doubleValue();
        if(max.compareTo(second.doubleValue())<0) max = second.doubleValue();
        if(max.compareTo(third.doubleValue())<0) max = third.doubleValue();
        return max;
    }

    double average(){
        double sum = first.doubleValue() + second.doubleValue() + third.doubleValue();
        return sum/3;
    }

    void sort(){
        if(first.doubleValue()>second.doubleValue()) {
            T tmp = second;
            second = first;
            first = tmp;
        }
        if(second.doubleValue()>third.doubleValue()) {
            T tmp = third;
            third = second;
            second = tmp;
        }
        if(first.doubleValue()>second.doubleValue()){
            T tmp = second;
            second = first;
            first = tmp;
        }
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f",first.doubleValue(),second.doubleValue(),third.doubleValue());
    }
}


