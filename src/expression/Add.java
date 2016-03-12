package expression;

public class Add extends Operation {
    public Add(Evaluable first, Evaluable second) {
        super(first, second);
    }

    protected double calculate(double a, double b) {
        return a + b;
    }
}
