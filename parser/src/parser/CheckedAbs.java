package parser;

import parser.exceptions.OverflowException;

public class CheckedAbs implements TripleExpression {
    private TripleExpression value;

    public CheckedAbs(TripleExpression value) {
        this.value = value;
    }

    public int evaluate(int x, int y, int z) {
        int a = value.evaluate(x, y, z);
        if (a != Integer.MIN_VALUE) {
            return (a >= 0 ? a : -a);
        } else {
            throw new OverflowException("Abs overflow", a);
        }
    }
}
