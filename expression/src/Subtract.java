public class Subtract extends Operation {
    public Subtract(Evaluable first, Evaluable second) {
        super(first, second);
    }

    protected double calculate(double a, double b) {
        return a - b;
    }
}