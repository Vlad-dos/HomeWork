package expression;

public abstract class Operation implements AllExpression {
    protected AllExpression first, second;

    public Operation(AllExpression first, AllExpression second) {
        this.first = first;
        this.second = second;
    }

    protected abstract int calculate(int a, int b);

    protected abstract double calculate(double a, double b);

    public int evaluate(int x) {
        return calculate(first.evaluate(x), second.evaluate(x));
    }

    public double evaluate(double x) {
        return calculate(first.evaluate(x), second.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return calculate(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
