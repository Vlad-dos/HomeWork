package parser;

import parser.exceptions.OverflowException;

public class CheckedMultiply extends CheckedBinaryOperation {
    public CheckedMultiply(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public CheckedMultiply() {
        super();
    }

    protected int calculate(int a, int b) {
        if (a == 0) {
            return a * b;
        }
        if (a == -1) {
            if (b == Integer.MIN_VALUE) {
                throw new OverflowException("Multiply overflow", a, b);
            } else {
                return a * b;
            }
        }
        int bMax = Integer.MAX_VALUE / a;
        int bMin = Integer.MIN_VALUE / a;
        if (bMax < bMin) {
            int w = bMin;
            bMin = bMax;
            bMax = w;
        }
        if (!(bMin <= b && b <= bMax)) {
            throw new OverflowException("Multiply overflow", a, b);
        } else {
            return a * b;
        }
    }
}
