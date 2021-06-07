package mk.ukim.finki.aud7;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Birthdays {

    public static boolean singleTrial(int people){
        int counter = 0;
        Random random = new Random();
        Set<Integer> birthdays = new HashSet<>();
        for(int i = 0; i<people;i++){
            int rdmBirthday = random.nextInt(364)+1;
            if(birthdays.contains(rdmBirthday))
                return true;
            birthdays.add(rdmBirthday);
        }
        return false;

    }

    public static double experiment(int people){
        int counter = 0;

        for(int i = 0; i < 5000;i++){
            if(singleTrial(people)) ++counter;
        }
        return counter/5000.0;
    }

    public static void main(String[] args) {
        for(int i = 2; i <= 50; i++){
            System.out.printf("%d  ->  %.5f \n", i , experiment(i));
        }
    }
}
