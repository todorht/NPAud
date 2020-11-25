//package mk.ukim.finki.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurement(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}
class Measurement implements Comparable<Measurement>{
    float temperature;
    float wind;
    float humidity;
    float visibility;
    Date date;

    public Measurement(float temperature, float wind, float humidity, float visibility, Date date) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.date = date;
    }


    @Override
    public int compareTo(Measurement o) {
        if(Math.abs(date.getTime() - o.date.getTime()) < 150000){
            return 0;
        } return date.compareTo(o.date);
    }
    //24.6 80.2 km/h 28.7% 51.7 km Tue Dec 17 23:40:15 CET 2013
    @Override
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s",temperature,wind,humidity,visibility,date);
    }
}
// vashiot kod ovde
class WeatherStation{
    int days;
    List<Measurement> measurements;

    public WeatherStation(int days) {
        this.days = days;
        this.measurements = new ArrayList<>(days);
    }

    public void addMeasurement(float temperature, float wind, float humidity, float visibility, Date date){
        Measurement measurement = new Measurement(temperature, wind, humidity, visibility, date);

        if(measurements.size()==0) measurements.add(measurement);
        else if(!(measurement.compareTo(measurements.get(measurements.size()-1))==0)) measurements.add(measurement);

        measurements.removeIf(m->date.getTime() - m.date.getTime()>=this.days * 86400000);
    }

    public int total(){
        return this.measurements.size();
    }

    public void status(Date from, Date to){
        List<Measurement> rez = measurements.stream().filter(m->!m.date.before(from) && !m.date.after(to)).collect(Collectors.toList());
        if(rez.size()==0) throw new RuntimeException();

        rez.forEach(measurement -> System.out.println(measurement.toString()));
        double average = rez.stream().mapToDouble(m->m.temperature).average().orElse(0);
        System.out.printf("Average temperature: %.2f",average);
    }
}