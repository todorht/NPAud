package prvKolovium;

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

// vashiot kod ovde
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
    //24.6 80.2 km/h 28.7% 51.7 km Tue Dec 17 23:40:15 CET 2013
    @Override
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s",temperature,wind,humidity,visibility,date);
    }

    @Override
    public int compareTo(Measurement other) {
        if (Math.abs(date.getTime() - other.date.getTime()) < 150000)
            return 0;
        return date.compareTo(other.date);
    }
}

class WeatherStation{
    int days;
    List<Measurement> measurements;

    public WeatherStation(int days) {
        this.days = days;
        this.measurements = new ArrayList<>();
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date){
        Measurement measurement = new Measurement(temperature, wind, humidity, visibility, date);
        boolean flag=true;
        for(Measurement m: measurements){
            if (measurement.compareTo(m) == 0) {
                flag = false;
                break;
            }
        }
        if(flag) measurements.add(measurement);

        this.measurements.removeIf(m->date.getTime() - m.date.getTime()>=this.days * 86400000);
    }

    public int total(){
        return this.measurements.size();
    }

    public void status(Date from, Date to){
        List<Measurement> status = measurements.stream().filter(m-> !(m.date.before(from) || m.date.after(to))).collect(Collectors.toList());
        if(status.size()==0) throw new RuntimeException();
        for(Measurement m:status){
            System.out.println(m.toString());
        }
        double average = status.stream().mapToDouble(m->m.temperature).average().orElse(0);
        System.out.printf("Average temperature: %.2f", average);
    }
}