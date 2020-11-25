package prvKolovium;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TimesTest {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

class UnsupportedFormatException extends RuntimeException {
    public UnsupportedFormatException(String line){
        super(line);
    }
}
class InvalidTimeException extends RuntimeException{

}
enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}

class Time{
    int hour;
    int min;

    public Time(String line) throws UnsupportedFormatException {
        String[] parts = new String[0];
        if(line.charAt(1)==':' || line.charAt(2)==':') {
            parts = line.split(":");
            this.hour = Integer.parseInt(parts[0]);
            this.min = Integer.parseInt(parts[1]);
        }else if(line.charAt(1)=='.' || line.charAt(2)=='.') {
            parts = line.split("\\.");
            this.hour = Integer.parseInt(parts[0]);
            this.min = Integer.parseInt(parts[1]);
        }else throw new UnsupportedFormatException(line);
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String toStringAMPM(){
        String part="AM";
        int h=hour;
        if(h==0){
            h+=12;
        } else if(h==12) {
            part="PM";
        }else if(h>12){
            h-=12;
            part="PM";
        }
        return String.format("%2d:%02d %s",h,min,part);}

    @Override
    public String toString() {
        return String.format("%2d:%02d",hour,min);
    }
}

class TimeTable{

    List<Time> timeList;

    public TimeTable() {
        this.timeList = new ArrayList<>();
    }

    public void readTimes(InputStream in) throws UnsupportedFormatException,InvalidTimeException{
        Scanner sc = new Scanner(in);
        while (sc.hasNext()){
            String line = sc.next();
            timeList.add(new Time(line));
        }
    }

    public void writeTimes(OutputStream out, TimeFormat format){
        PrintWriter pw = new PrintWriter(out);
        final Function<Time,String> toString = time->{
            if(format.equals(TimeFormat.FORMAT_24)) return time.toString();
            else return time.toStringAMPM();
        };
        List<String> timesToPrint = timeList.stream().sorted(Comparator.comparing(Time::getHour).thenComparing(Time::getMin))
                .map(toString).collect(Collectors.toList());
        timesToPrint.forEach(pw::println);
        pw.flush();
    }
}