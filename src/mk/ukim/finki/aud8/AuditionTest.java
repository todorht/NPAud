package mk.ukim.finki.aud8;

import java.util.*;

class Participant {
    private String city;
    private String code;
    private String name;
    private int age;

    public Participant(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }


//    @Override
//    public int compareTo(Participant o1) {
//        int res = this.name.compareTo(o1.name);
//
////        if(res==0)
////            return Integer.compare(o1.age, o1.age);
////        else return res;
//
//        return res==0 ? Integer.compare(this.age, o1.age): res;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", code,name,age);
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

class Audition {
    Map<String, Set<Participant>> participantByCity;

    public Audition() {
        this.participantByCity = new HashMap<>();
    }


    public void addParticpant(String city, String code, String name, int age) {
        //1. Create a empty tree set for a city that is being added for the first time
        participantByCity.putIfAbsent(city, new HashSet<>());
        //istoto od gorniot kod
//        if(!participantByCity.containsKey(city)){
//            participantByCity.put(city, new TreeSet<>());
//        }

        //2. Fill the set by city with new participant
        participantByCity.get(city).add(new Participant(city, code, name, age));
        //Druga varijanta
//        participantByCity.computeIfPresent(city,(k,v) ->{
//            v.add(new Participant(city, code, name, age));
//            return v;
//        });
    }


    public void listByCity(String city) {
        participantByCity.getOrDefault(city, new HashSet<>())
                .stream().sorted(Comparator.comparing(Participant::getName).thenComparing(Participant::getAge).thenComparing(Participant::getCode))
                .forEach(participant -> System.out.println(participant.toString()));
    }
}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}