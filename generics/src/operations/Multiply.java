package operations;

import calculables.Calculable;
import generics.TripleExpression;

public class Multiply<T> extends BinaryOperation<T> {
    public Multiply(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public Multiply() {
        super();
    }

    protected Calculable<T> calculate(Calculable<T> a, Calculable<T> b) {
        return a.multiply(b);
    }
}