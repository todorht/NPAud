package mk.ukim.finki.aud4.Chitanje;

public class Calculator {

    private Strategy strategy;
    private double result;

    public Calculator(){
        this.result = 0.0;
    }

    public double getResult() {
        return result;
    }

    public String init(){
        return String.format("result = %.2f", result);
    }

    public String execute(char operation, double value) throws UnknownOperatorException {
        if(operation=='+')strategy = new Addition(); //
        else if(operation=='-') strategy = new Subtraction();
        else if(operation=='*') strategy = new Multiplacation();
        else if(operation=='/') strategy = new Division();
        else throw new UnknownOperatorException(operation);

        result = strategy.doOperation(result, value);
        return String.format("result %c %.2f = %.2f",operation,value,result);
    }

    @Override
    public String toString() {
        return String.format("update result = %.2f", result);
    }
}

