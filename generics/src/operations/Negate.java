package operations;

import calculables.Calculable;
import generics.TripleExpression;

public class Negate<T> implements TripleExpression<T> {
    private TripleExpression<T> value;

    public Negate(TripleExpression<T> value) {
        this.value = value;
    }

    public Calculable<T> evaluate(Calculable<T> x, Calculable<T> y, Calculable<T> z) {
        return value.evaluate(x, y, z).negate();
    }
}
