package parser;

public class CheckedSqrt implements TripleExpression {
    private TripleExpression value;

    public CheckedSqrt(TripleExpression value) {
        this.value = value;
    }

    private int sqrt(int a) {
        int res = 0;
        int bit = 1 << 30;

        while (bit > a) {
            bit >>= 2;
        }

        while (bit != 0) {
            if (a >= res + bit) {
                a -= res + bit;
                res = (res >> 1) + bit;
            } else {
                res >>= 1;
            }
            bit >>= 2;
        }
        return res;
    }

    public int evaluate(int x, int y, int z) {
        int a = value.evaluate(x, y, z);
        if (a >= 0) {
            return sqrt(a);
        } else {
            throw new ArithmeticException("Abs overflow");
        }
    }
}
