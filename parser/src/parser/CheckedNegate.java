package parser;

public class CheckedNegate implements TripleExpression {
    TripleExpression value;

    public CheckedNegate(TripleExpression value) {
        this.value = value;
    }

    public int evaluate(int x, int y, int z) {
        int a = value.evaluate(x, y, z);
        if (a == Integer.MIN_VALUE) {
            throw new ArithmeticException("Negate overflow");
        } else {
            return -a;
        }
    }
}
