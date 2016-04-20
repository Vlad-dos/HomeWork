package operations;

import calculables.Calculable;
import generics.TripleExpression;

public abstract class BinaryOperation<T> implements TripleExpression<T> {
    private TripleExpression<T> first, second;

    public BinaryOperation(TripleExpression<T> first, TripleExpression<T> second) {
        this.first = first;
        this.second = second;
    }

    public BinaryOperation() {
    }

    protected abstract Calculable<T> calculate(Calculable<T> a, Calculable<T> b);

    public Calculable<T> evaluate(Calculable<T> x, Calculable<T> y, Calculable<T> z) {
        return calculate(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    public void setFirst(TripleExpression<T> expression) {
        first = expression;
    }

    public void setSecond(TripleExpression<T> expression) {
        second = expression;
    }
}
