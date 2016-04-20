package operations;

import calculables.Calculable;
import generics.TripleExpression;

public class Abs<T> implements TripleExpression<T> {
    private TripleExpression<T> value;

    public Abs(TripleExpression<T> value) {
        this.value = value;
    }

    public Calculable<T> evaluate(Calculable<T> x, Calculable<T> y, Calculable<T> z) {
        return value.evaluate(x, y, z).abs();
    }
}