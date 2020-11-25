package mk.ukim.finki.aud4.Chitanje;

public class UnknownOperatorException extends Exception {

    public UnknownOperatorException(char operator){
        super(String.format("%c ne e validen", operator));
    }
}
