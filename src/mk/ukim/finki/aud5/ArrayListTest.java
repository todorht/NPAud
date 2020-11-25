package mk.ukim.finki.aud5;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTest {

    public static void main(String[] args) {

        ArrayList<Integer> integers = new ArrayList<>();
        List<Integer> biggerArray = new ArrayList<>();
        biggerArray.add(2);
        biggerArray.add(3);

        integers.add(5);

        integers.add(0,7);
        integers.add(1,8);

        integers.contains(4);  //proverka dali imame 4 vo listata


        biggerArray.addAll(integers);


    }
}
