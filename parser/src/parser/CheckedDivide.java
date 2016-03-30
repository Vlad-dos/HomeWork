package parser;

public class CheckedDivide extends CheckedBinaryOperation {
    public CheckedDivide(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int calculate(int a, int b) {
        if (b == 0 || (b == -1 && a == Integer.MIN_VALUE)) {
            throw new ArithmeticException("Division by zero");
        } else {
            return a / b;
        }
    }
}
