package expression;

public class Const implements Evaluable {
    private double value;

    Const(double value) {
        this.value = value;
    }

    public double evaluate(double x) {
        return value;
    }
}
