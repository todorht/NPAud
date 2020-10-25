package aud1;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MatrixTester {

    public static double sum(double[][] a){
        double sum = 0;
        for (int i = 0; i < a.length; i++){
            for (int j = 0; j < a[0].length; j++)
                sum += a[i][j];
        }
        return sum;
    }

    public static double sumStream(double[][] a){
        return Arrays.stream(a)
                .mapToDouble(row -> Arrays.stream(row).sum())
                .sum();
    }

    public static double average(double[][] a){
        return sum(a) / (a.length * a[0].length);
    }

    public static void main(String[] args) {
        double[][] a = {{1,2,3},{1,2,3}};
        System.out.println(sum(a));
        System.out.println(sumStream(a));
        System.out.println(average(a));
    }

}
