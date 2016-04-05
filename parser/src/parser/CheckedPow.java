package parser;

import parser.exceptions.OverflowException;

public class CheckedPow extends CheckedBinaryOperation {
    public CheckedPow(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public CheckedPow() {
        super();
    }

    int mul(int a, int b) {
        return (new CheckedMultiply(new Const(a), new Const(b))).evaluate(0, 0, 0);
    }

    protected int calculate(int a, int b) {
        if (b < 0) {
            throw new ArithmeticException("Exponent below zero.");
        }
        if (b == 0 && a == 0) {
            throw new ArithmeticException("Zero power zero.");
        }
        int result = 1;
        while (b > 0) {
            try {
                if (b % 2 != 0) {
                    result = mul(result, a);
                    b--;
                } else {
                    a = mul(a, a);
                    b /= 2;
                }
            } catch (OverflowException e) {
                throw new OverflowException("Power overflow", a, b);
            }
        }

        return result;
    }
}
