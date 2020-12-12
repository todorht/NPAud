package mk.ukim.finki.test;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}
abstract class Temperature{
    int value;

    public Temperature(int value){
        this.value = value;
    }

    abstract double getCelsius();
    abstract double getFahrenheit();

    public static Temperature createTemperature(String part){
        char type = part.charAt(part.length()-1);
        int value = Integer.parseInt(part.substring(0,part.length()-1));
        if(type=='C')return new CTemperature(value);
        else return new FTemperature(value);
    }
}
class CTemperature extends Temperature{

    public CTemperature(int value) {
        super(value);
    }

    @Override
    double getCelsius() {
        return value;
    }

    @Override
    double getFahrenheit() {
        return (value*9.0)/5 + 32.0;
    }

    @Override
    public String toString() {
        return String.format("%dC",value);
    }
}

class FTemperature extends Temperature{

    public FTemperature(int value) {
        super(value);
    }

    @Override
    double getCelsius() {
        return (value-32)*5/9.0;
    }

    @Override
    double getFahrenheit() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%sF",value);
    }
}

class DailyMeasurement implements Comparable<DailyMeasurement>{
    int day;
    List<Temperature> temperatures;

    public DailyMeasurement(int day, List<Temperature> temperatures) {
        this.day = day;
        this.temperatures = temperatures;
    }

    public static DailyMeasurement createDailyMeasurement(String line){
        String[] parts = line.split("\\s+");
        int day = Integer.parseInt(parts[0]);
        List<Temperature> temperatures = Arrays.stream(parts).skip(1).map(Temperature::createTemperature).collect(Collectors.toList());
        return new DailyMeasurement(day,temperatures);
    }


    @Override
    public int compareTo(DailyMeasurement o) {
        return Integer.compare(this.day,o.day);
    }

    public String toString(char scale){
        DoubleSummaryStatistics dss = temperatures.stream().mapToDouble(t->{
            if(scale=='C') return t.getCelsius();
            else return t.getFahrenheit();
        }).summaryStatistics();

        return String.format("%3d: Count: %3d Min: %6.2f%c Max: %6.2f%c Avg: %6.2f%c",
                day,dss.getCount(),dss.getMin(),scale,dss.getMax(),scale,dss.getAverage(),scale);
    }
}

class DailyTemperatures{
    List<DailyMeasurement> dailyMeasurements;

    public DailyTemperatures() {
        this.dailyMeasurements = new ArrayList<>();
    }

    void readTemperatures(InputStream in){
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        this.dailyMeasurements = br.lines().map(DailyMeasurement::createDailyMeasurement).collect(Collectors.toList());
    }

    void writeDailyStats(OutputStream out, char scale){
        PrintWriter pw = new PrintWriter(out);
        dailyMeasurements.stream().sorted(Comparator.naturalOrder()).forEach((dailyMeasurement)->pw.println(dailyMeasurement.toString(scale)));
        pw.flush();
    }
}