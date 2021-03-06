package parser;

import parser.exceptions.OverflowException;

public class CheckedNegate implements TripleExpression {
    private TripleExpression value;

    public CheckedNegate(TripleExpression value) {
        this.value = value;
    }

    public int evaluate(int x, int y, int z) {
        int a = value.evaluate(x, y, z);
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("Negate overflow", a);
        } else {
            return -a;
        }
    }
}
