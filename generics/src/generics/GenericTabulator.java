package generics;

import calculables.*;

import java.math.BigInteger;

public class GenericTabulator implements Tabulator {
    private int x1, x2, y1, y2, z1, z2;

    public <T> Object[][][] makeTable(TripleExpression<T> expression, CalculableCreator<T> creator) {
        Calculable<T> x = creator.create();
        Calculable<T> y = creator.create();
        Calculable<T> z = creator.create();
        Object[][][] ans = new Object[x2 - x1 + 1][][];
        for (int i = x1; i <= x2; i++) {
            ans[i - x1] = new Object[y2 - y1 + 1][];
            for (int j = y1; j <= y2; j++) {
                ans[i - x1][j - y1] = new Object[z2 - z1 + 1];
                for (int k = z1; k <= z2; k++) {
                    x.setValue(i);
                    y.setValue(j);
                    z.setValue(k);
                    try {
                        ans[i - x1][j - y1][k - z1] = expression.evaluate(x, y, z).getValue();
                    } catch (ArithmeticException e) {
                        ans[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return ans;
    }

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
        switch (mode) {
            case "u":
                ExpressionParser<Integer> parserInteger = new ExpressionParser<>(CalculableInteger::new);
                return makeTable(parserInteger.parse(expression), CalculableInteger::new);
            case "d":
                ExpressionParser<Double> parserDouble = new ExpressionParser<>(CalculableDouble::new);
                return makeTable(parserDouble.parse(expression), CalculableDouble::new);
            case "bi":
                ExpressionParser<BigInteger> parserBigInteger = new ExpressionParser<>(CalculableBigInteger::new);
                return makeTable(parserBigInteger.parse(expression), CalculableBigInteger::new);
            case "i":
                ExpressionParser<Integer> parserCheckedInteger = new ExpressionParser<>(CalculableCheckedInteger::new);
                return makeTable(parserCheckedInteger.parse(expression), CalculableCheckedInteger::new);
            case "f":
                ExpressionParser<Float> parserFloat = new ExpressionParser<>(CalculableFloat::new);
                return makeTable(parserFloat.parse(expression), CalculableFloat::new);
            case "b":
                ExpressionParser<Byte> parserByte = new ExpressionParser<>(CalculableByte::new);
                return makeTable(parserByte.parse(expression), CalculableByte::new);
            default:
                return null;
        }
    }
}
