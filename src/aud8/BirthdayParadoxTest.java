package aud8;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BirthdayParadoxTest {

    private static Random random = new Random();

    public static void main(String[] args) {
        for(int i=2; i<=50; i++){
            System.out.println(String.format("For %d people, the probability of two birthdays is about %.5f", i, experiment(i)));
        }
    }

    private static double experiment(int person) {
        int count=0;
        for(int i=0; i<5000;i++){
            if(trial(person))
                ++count;
        }
        return count/5000.0;
    }

    private static boolean trial(int person) {
        Set<Integer> birthdays = new HashSet<>();
        for(int i=0;i<person; i++){
            int newBirthday = random.nextInt(365) + 1;
            if(birthdays.contains(newBirthday))
                return true;
            birthdays.add(newBirthday);
        }
        return false;
    }
}
