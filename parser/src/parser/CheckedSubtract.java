package parser;

public class CheckedSubtract extends CheckedBinaryOperation {
    public CheckedSubtract(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int calculate(int a, int b) {
        int aMin = (b > 0 ? (Integer.MIN_VALUE + b) : Integer.MIN_VALUE);
        int aMax = (b < 0 ? (Integer.MAX_VALUE + b) : Integer.MAX_VALUE);
        if (!(aMin <= a && a <= aMax)) {
            throw new ArithmeticException("Subtract overflow");
        } else {
            return a - b;
        }
    }
}