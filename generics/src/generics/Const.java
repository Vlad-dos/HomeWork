package generics;

import calculables.Calculable;

public class Const<T> implements TripleExpression<T> {
    private Calculable<T> value;

    public Const(Calculable<T> value) {
        this.value = value;
    }

    public Calculable<T> evaluate(Calculable<T> x, Calculable<T> y, Calculable<T> z) {
        return value;
    }
}
