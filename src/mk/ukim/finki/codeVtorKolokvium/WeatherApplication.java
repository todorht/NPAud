package mk.ukim.finki.codeVtorKolokvium;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class WeatherApplication {

    public static void main(String[] args) {
        WeatherDispatcher weatherDispatcher = new WeatherDispatcher();

        CurrentConditionsDisplay currentConditions = new CurrentConditionsDisplay(weatherDispatcher);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherDispatcher);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            weatherDispatcher.setMeasurements(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
            if(parts.length > 3) {
                int operation = Integer.parseInt(parts[3]);
                if(operation==1) {
                    weatherDispatcher.remove(forecastDisplay);
                }
                if(operation==2) {
                    weatherDispatcher.remove(currentConditions);
                }
                if(operation==3) {
                    weatherDispatcher.register(forecastDisplay);
                }
                if(operation==4) {
                    weatherDispatcher.register(currentConditions);
                }

            }
        }
    }
}

// vashiot kod ovde


interface Subscriber{
    public void update(float temperature, float humidity, float pressure);
}

class CurrentConditionsDisplay implements Subscriber{

    float currentTemperature;
    float currentHumidity;

    public CurrentConditionsDisplay(WeatherDispatcher weatherDispatcher){
        weatherDispatcher.register(this);
        this.currentHumidity = 0.0F;
        this.currentTemperature = 0.0F;
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
            System.out.printf("Temperature: %.1fF\n", temperature);
            System.out.printf("Humidity: %.1f%%\n", humidity);

    }
}

class WeatherDispatcher{

    LinkedList<Subscriber> subscribers;

    public WeatherDispatcher(){
        subscribers= new LinkedList<>();
    }

    public void setMeasurements(float temperature, float humidity, float pressure){
        subscribers.forEach(subscriber -> subscriber.update(temperature,humidity,pressure));
        System.out.println();
    }

    void register(Subscriber subscriber){
        if(subscribers.contains(subscriber))
            return;

        if(subscriber instanceof CurrentConditionsDisplay) subscribers.addFirst(subscriber);
        else if(subscriber instanceof ForecastDisplay) subscribers.addLast(subscriber);
    }

    void remove(Subscriber subscriber){
        this.subscribers.remove(subscriber);
    }


}

class ForecastDisplay implements Subscriber{

    float beforePressure;

    public ForecastDisplay(WeatherDispatcher weatherDispatcher){

        this.beforePressure = 0.0F;
        weatherDispatcher.register(this);
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        System.out.print("Forecast:");
        switch (Float.compare(beforePressure, pressure)) {
            case 1:
                System.out.println(" Cooler");
                break;
            case 0:
                System.out.println(" Same");
                break;
            case -1:
                System.out.println(" Improving");
                break;
        }
        beforePressure=pressure;
    }
}