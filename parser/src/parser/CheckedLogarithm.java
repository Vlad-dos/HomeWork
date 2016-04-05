package parser;

import parser.exceptions.OverflowException;

public class CheckedLogarithm extends CheckedBinaryOperation {
    public CheckedLogarithm(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public CheckedLogarithm() {
        super();
    }

    int pow(int a, int b) {
        return (new CheckedPow(new Const(a), new Const(b))).evaluate(0, 0, 0);
    }

    protected int calculate(int a, int b) {
        if (b < 2) {
            throw new ArithmeticException("Logarithm base below two");
        }
        if (a < 1) {
            throw new ArithmeticException("Logarithm exponent below one");
        }
        int l = 0;
        int r = a;
        while (r - l > 1) {
            int m = (r + l) / 2;
            boolean overflow = false;
            int value = 0;
            try {
                value = pow(b, m);
            } catch (OverflowException e) {
                overflow = true;
            }
            if (value > a || overflow) {
                r = m;
            } else {
                l = m;
            }
        }
        return l;
    }
}
