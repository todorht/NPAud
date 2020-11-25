package mk.ukim.finki.test;

import javax.print.attribute.standard.NumberUp;
import java.util.Scanner;

public class GenericFractionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
            GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
            GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
            GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch(ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

}
class ZeroDenominatorException extends Exception{
    @Override
    public String getMessage() {
        return String.format("Denominator cannot be zero");
    }
}

// вашиот код овде
class GenericFraction<T extends Number, U extends Number>{
    T numerator;
    U denominator;

    public GenericFraction(T numerator, U denominator) throws ZeroDenominatorException {
        this.numerator = numerator;
        if(denominator.intValue()!=0) {
            this.denominator = denominator;
        }else throw new ZeroDenominatorException();
    }

    GenericFraction<Double,Double> add(GenericFraction<? extends Number,? extends Number> gf) throws ZeroDenominatorException {
        double n;
        double d;
        if(this.denominator.doubleValue()==gf.denominator.doubleValue()){
            n = this.numerator.doubleValue()+gf.numerator.doubleValue();
            d = this.denominator.doubleValue();
        }else {
            d = this.denominator.doubleValue()*gf.denominator.doubleValue();
            n = (this.numerator.doubleValue()*(d/this.denominator.doubleValue())) + (gf.numerator.doubleValue()*(d/gf.denominator.doubleValue()));
        }
        for(double i=n;i>0;i--){
            if(n%i==0 && d%i==0){
                n=n/i;
                d=d/i;
            }
        }
        return new GenericFraction<>(n,d);
    }

    double toDouble(){
        return this.numerator.doubleValue()/this.denominator.doubleValue();
    }

    @Override
    public String toString() {
        return String.format("%.2f / %.2f",this.numerator.doubleValue(),this.denominator.doubleValue());
    }
}