package operations;

import calculables.Calculable;
import generics.TripleExpression;

public class Subtract<T> extends BinaryOperation<T> {
    public Subtract(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public Subtract() {
        super();
    }

    protected Calculable<T> calculate(Calculable<T> a, Calculable<T> b) {
        return a.subtract(b);
    }
}
