package kolokvium1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
            ws.addMeasurment(temp, wind, hum, vis, date);
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

class WeatherStation {
    List<Measure> measures;
    int days;

    public WeatherStation(int days) {
        this.days = days;
        this.measures = new ArrayList<>(days);
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date){
        Measure measure = new Measure(temperature, humidity, wind, visibility, date);
        boolean flag = true;
        for(Measure measure1 : measures){
            if(measure1.compareTo(measure)==0)
                flag = false;
        }

        if(flag) measures.add(measure);
        measures.removeIf(m ->date.getTime() - m.date.getTime() >= (this.days * 86400000));
    }

    public int total(){
        return this.measures.size();
    }

    public void status(Date from, Date to){
        List<Measure> interval = measures.stream().filter(m -> !(m.date.before(from) || m.date.after(to))).collect(Collectors.toList());
        if(interval.size() == 0)
            throw new RuntimeException();
        for (Measure measure: interval)
            System.out.println(measure.toString());
        double average = interval.stream().mapToDouble(m -> m.temperature).average().orElse(0);
        System.out.println(String.format("Average temperature: %.2f", average));
    }
}

class Measure implements Comparable<Measure>{
    float temperature;
    float humidity;
    float wind;
    float visibility;
    Date date;

    public Measure(float temperature, float humidity, float wind, float visibility, Date date) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind = wind;
        this.visibility = visibility;
        this.date = date;
    }


    @Override
    public int compareTo(Measure o) {
        if(Math.abs(this.date.getTime() - o.date.getTime()) < 150000)
            return 0;
        return date.compareTo(o.date);
    }

    @Override
    public String toString() {
        return String.format("%.1f %.1f km//h %.1f %% %.1f k m %s", temperature, wind, humidity, visibility, date.toString());
    }
}