package prvKolovium;

import java.util.Scanner;

class GenericFractionTest {
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

// вашиот код овде
class ZeroDenominatorException extends Exception{
    public ZeroDenominatorException(){
        super("Denominator cannot be zero");
    }
}

class GenericFraction<T extends Number,U extends Number>{
    T numerator;
    U denominator;

    public GenericFraction(T numerator, U denominator) throws ZeroDenominatorException {
        this.numerator = numerator;
        if(denominator.intValue()==0) throw new ZeroDenominatorException();
        this.denominator = denominator;
    }

    public GenericFraction<Double,Double> add(GenericFraction<? extends Number, ? extends Number> gf) throws ZeroDenominatorException {
        double newNumerator;
        double newDenominator;
        if(this.denominator.doubleValue() == gf.denominator.doubleValue()){
            newNumerator = this.numerator.doubleValue()+gf.numerator.doubleValue();
            newDenominator = this.denominator.doubleValue();
        }else {
            newDenominator = this.denominator.doubleValue()*gf.denominator.doubleValue();
            newNumerator = (this.numerator.doubleValue()*(newDenominator/this.denominator.doubleValue())) + (gf.numerator.doubleValue()*(newDenominator/gf.denominator.doubleValue()));
        }
        for(int i = (int) newNumerator; i>0; i--){
            if(newNumerator%i==0 && newDenominator%i==0){
                newDenominator= newDenominator/i;
                newNumerator = newNumerator/i;
            }
        }
        return new GenericFraction<>(newNumerator,newDenominator);
    }

    public double toDouble(){
        return this.numerator.doubleValue()/this.denominator.doubleValue();
    }

    @Override
    public String toString() {
        return String.format("%.2f / %.2f",this.numerator.doubleValue(), this.denominator.doubleValue());
    }
}