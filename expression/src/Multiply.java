public class Multiply extends Operation {
    public Multiply(Evaluable first, Evaluable second) {
        super(first, second);
    }

    protected double calculate(double a, double b) {
        return a * b;
    }
}