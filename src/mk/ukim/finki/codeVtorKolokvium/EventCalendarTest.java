package mk.ukim.finki.codeVtorKolokvium;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                throw new WrongDateException(date);
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

// vashiot kod ovde


class WrongDateException extends RuntimeException{

    Date date;

    public WrongDateException(Date date){
        this.date = date;
    }

}

class Event{
    String name;
    String location;
    Date date;

    public Event(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd MMM, yyyy HH:mm");
        String dateString = df.format(date);
        return String.format("%s at %s, %s", dateString,location,name);
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }
}

class EventCalendar{
    int year;
    Map<Integer, List<Event>> events;

    public EventCalendar(int year) {
        this.year = year;
        events = new TreeMap<>();
    }

    public void addEvent(String name, String location, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        if(cal.get(Calendar.YEAR)!=year){
            try {
                throw new WrongDateException(date);
            } catch (WrongDateException e) {
                SimpleDateFormat sdf=new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                String dateFormat = sdf.format(date);
                System.out.printf("Wrong date: %s\n",dateFormat);
            }
        }else {
            if(events.containsKey(month)){
                events.get(month).add(new Event(name,location,date));
            }else {
                events.put(month,new ArrayList<>());
                events.get(month).add(new Event(name,location,date));
            }
        }
    }


    public void listEvents(Date date) {
//           List<Event> tmp = events.stream()
//                    .filter(event -> event.date.getTime()>date.getTime() && event.date.getTime()<date.getTime()+(24*60*60000))
//                    .sorted(Comparator.comparing(Event::getDate).thenComparing(Event::getName)).collect(Collectors.toList());
//           if(tmp.size()==0) System.out.println("No events on this day!");
//           else tmp.forEach(System.out::println);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        List<Event> tmp = new ArrayList<>();
        if(events.containsKey(month)) {
            tmp = events.get(month).stream().filter(event -> {
                cal.setTime(event.date);
                if (day == cal.get(Calendar.DAY_OF_MONTH)) return true;
                else return false;
            }).sorted(Comparator.comparing(Event::getDate).thenComparing(Event::getName)).collect(Collectors.toList());
        }else {
        }
        if(tmp.size()==0) System.out.println("No events on this day!");
          else {
              tmp.forEach(System.out::println);
        }
    }

    public void listByMonth() {
        for(int i = 0;i<12;i++){
            if(events.containsKey(i)) {
                int count = (int) events.get(i).stream().count();
                System.out.printf("%d : %d\n",i+1, count);
            }
            else System.out.printf("%d : 0\n", i+1);
        }
    }
}