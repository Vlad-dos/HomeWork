public class Divide extends Operation {
    public Divide(AllExpression first, AllExpression second) {
        super(first, second);
    }

    protected int calculate(int a, int b) {
        return a / b;
    }

    protected double calculate(double a, double b) {
        return a / b;
    }
}