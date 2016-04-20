package operations;

import calculables.Calculable;
import generics.TripleExpression;

public class Add<T> extends BinaryOperation<T> {
    public Add(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public Add() {
        super();
    }

    protected Calculable<T> calculate(Calculable<T> a, Calculable<T> b) {
        return a.add(b);
    }
}
