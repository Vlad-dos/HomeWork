public abstract class Operation implements Evaluable {
    protected Evaluable first, second;

    public Operation(Evaluable first, Evaluable second) {
        this.first = first;
        this.second = second;
    }

    protected abstract double calculate(double a, double b);

    public double evaluate(double x) {
        return calculate(first.evaluate(x), second.evaluate(x));
    }
}
