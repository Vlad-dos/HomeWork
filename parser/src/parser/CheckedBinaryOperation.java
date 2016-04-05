package parser;

public abstract class CheckedBinaryOperation implements TripleExpression {
    private TripleExpression first, second;

    public CheckedBinaryOperation(TripleExpression first, TripleExpression second) {
        this.first = first;
        this.second = second;
    }

    public CheckedBinaryOperation() {
    }

    protected abstract int calculate(int a, int b);

    public int evaluate(int x, int y, int z) {
        return calculate(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    public void setFirst(TripleExpression expression) {
        first = expression;
    }

    public void setSecond(TripleExpression expression) {
        second = expression;
    }
}
