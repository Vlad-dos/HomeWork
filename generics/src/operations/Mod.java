package operations;

import calculables.Calculable;
import generics.TripleExpression;

public class Mod<T> extends BinaryOperation<T> {
    public Mod(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public Mod() {
        super();
    }

    protected Calculable<T> calculate(Calculable<T> a, Calculable<T> b) {
        return a.mod(b);
    }
}