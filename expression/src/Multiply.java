public class Multiply extends Operation {
    public Multiply(AllExpression first, AllExpression second) {
        super(first, second);
    }

    protected int calculate(int a, int b) {
        return a * b;
    }

    protected double calculate(double a, double b) {
        return a * b;
    }
}