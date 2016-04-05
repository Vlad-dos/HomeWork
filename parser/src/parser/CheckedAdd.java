package parser;

import parser.exceptions.OverflowException;

public class CheckedAdd extends CheckedBinaryOperation {
    public CheckedAdd(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public CheckedAdd() {
        super();
    }

    protected int calculate(int a, int b) {
        int bMax = (a > 0 ? Integer.MAX_VALUE - a : Integer.MAX_VALUE);
        int bMin = (a < 0 ? Integer.MIN_VALUE - a : Integer.MIN_VALUE);
        if (!(bMin <= b && b <= bMax)) {
            throw new OverflowException("Add overflow", a, b);
        } else {
            return a + b;
        }
    }
}
