package mk.ukim.finki.aud5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterfaceTest {


    public static void main(String[] args) {

        List<Integer> numbers = new ArrayList<>();

//        Supplier<Integer> integerSupplier = new Supplier<Integer>() {
//            @Override
//            public Integer get() {
//                return new Random().nextInt();
//            }
//        };

        Supplier<Integer> integerSupplier = () -> new Random().nextInt();  //lambda expression

        //consumer  -- foreach statement .stream()
//        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) {
//                System.out.println(integer);
//            }
//        };

        Consumer<Integer> integerConsumer = integer ->  {
            numbers.add(integer);
            System.out.println(integer);
        };


        //Predicate filter/condition
        Predicate<Integer> lessThenFive = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer<5;
            }
        };

        //Predicate lambda
        Predicate<Integer> lessThenFiveLambda = integer -> integer < 5;

        //Function (.map) (flatMap)
//        Function<Integer, String> integerToStringMapper = new Function<Integer, String>() {
//            @Override
//            public String apply(Integer integer) {
//                return String.valueOf(integer.doubleValue());
//            }
//        };

        Function<Integer, String> integerToStringMapperLambda = integer -> String.valueOf(integer.doubleValue());
    }
}
