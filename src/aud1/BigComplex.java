package aud1;

import java.math.BigDecimal;

public class BigComplex {

    private final BigDecimal real;
    private final BigDecimal imaginery;

    public BigComplex(BigDecimal real, BigDecimal imaginery) {
        this.real = real;
        this.imaginery = imaginery;
    }



    public BigComplex add(BigComplex complex){
        return new BigComplex(this.real.add(complex.real), this.imaginery.add(complex.imaginery));
    }

    public BigComplex subtract(BigComplex complex){
        return new BigComplex(this.real.subtract(complex.real), this.imaginery.subtract(complex.imaginery));
    }

    public BigComplex multiply(BigComplex complex){
        return new BigComplex(this.real.multiply(complex.real), this.imaginery.multiply(complex.imaginery));
    }

    public BigComplex divide(BigComplex complex){
        return new BigComplex(this.real.divide(complex.real), this.imaginery.divide(complex.imaginery));
    }
}
