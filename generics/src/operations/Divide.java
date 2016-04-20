package operations;

import calculables.Calculable;
import generics.TripleExpression;

public class Divide<T> extends BinaryOperation<T> {
    public Divide(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public Divide() {
        super();
    }

    protected Calculable<T> calculate(Calculable<T> a, Calculable<T> b) {
        return a.divide(b);
    }
}