package expression;

public class Divide extends Operation {
    public Divide(Evaluable first, Evaluable second) {
        super(first, second);
    }

    protected double calculate(double a, double b) {
        return a / b;
    }
}