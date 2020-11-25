package mk.ukim.finki.generici;


import java.util.*;
import java.util.stream.Collectors;

public class MathGenericsTest {

//    public static <T extends Number> String statistic(List<T> numbers){
//    public static String statistic(List<? extends Number> numbers){

    public static <T extends Number> String statistic(List<T> numbers){

//
//        double min = Double.MAX_VALUE;
//        double max = Double.MIN_VALUE;
//        double sum = 0.0;
//
//        for(T n : numbers){ //T ili Number si raboti
//            if(n.doubleValue() < min) min = n.doubleValue();
//            if(n.doubleValue()>max) max = n.doubleValue();
//            sum+=n.doubleValue();
//        }
//
//        double average = sum/numbers.size();

        DoubleSummaryStatistics dss = new DoubleSummaryStatistics();
        numbers.forEach(number ->dss.accept(number.doubleValue()));

//        DoubleSummaryStatistics dss = numbers.stream().mapToDouble(i->i.doubleValue()).summaryStatistics()  istoto od gore

        double sumStd = 0;
        for(Number n: numbers){
            sumStd+=(n.doubleValue()-dss.getAverage())*(n.doubleValue()-dss.getAverage());
        }

        double std = Math.sqrt(sumStd/numbers.size());

        return String.format("Min: %.2f\nMax: %.2f\nAverage: %.2f\nStandad deviation: %.2f\n" +
                "Count: %d\nSum: %.2f",dss.getMin(),dss.getMax(),dss.getAverage(),std,dss.getCount(),dss.getSum());

    }

    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        Random random = new Random();
        for(int i =0;i<100;i++){
            integers.add(random.nextInt(100));
        }

        List<Double> doubles = new ArrayList<>();
        for(int i =0;i<100;i++){
            doubles.add(random.nextDouble()*50.0);
        }

        System.out.println(statistic(integers));
        System.out.println(statistic(doubles));
    }
}
