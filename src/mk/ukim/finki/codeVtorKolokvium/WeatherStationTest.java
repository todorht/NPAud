package mk.ukim.finki.codeVtorKolokvium;

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

class Measurement{
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
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s", temperature,wind,humidity,visibility,date);
    }
}

class WeatherStation{
    List<Measurement> measurements;
    int n;
    public WeatherStation(int n) {
        this.n = n;
        measurements = new ArrayList<>(n);
    }


    public void addMeasurment(float temp, float wind, float hum, float vis, Date date) {
        Measurement measurement = new Measurement(temp,wind,hum,vis,date);
        if(measurements.isEmpty()){
            measurements.add(measurement);
        }else if((measurement.date.getTime()-measurements.get(measurements.size()-1).date.getTime()) >= 150000){
            measurements.add(measurement);
        }

        measurements.removeIf(measurement1 -> (date.getTime()-measurement1.date.getTime()) >= n*86400000);
    }

    public int total() {
        return measurements.size();
    }

    public void status(Date from, Date to) {

       List<Measurement> tmp = measurements.stream()
               .filter(measurement -> measurement.date.getTime()>=from.getTime() && measurement.date.getTime()<=to.getTime())
               .collect(Collectors.toList());
       float sum = 0;
       for(Measurement measurement:tmp){
           sum+=measurement.temperature;
       }
        if(tmp.size()==0) System.out.println("java.lang.RuntimeException");
        else {
            tmp.forEach(System.out::println);
            System.out.printf("Average temperature: %.2f",(sum/tmp.size()));
        }
    }
}