package expression;

public class Const implements AllExpression {
    private double doubleValue;
    private int intValue;

    public Const(int value) {
        this.intValue = value;
        this.doubleValue = value;
    }

    public Const(double value) {
        this.doubleValue = value;
    }

    public int evaluate(int x, int y, int z) {
        return intValue;
    }

    public double evaluate(double x) {
        return doubleValue;
    }

    public int evaluate(int x) {
        return intValue;
    }
}
