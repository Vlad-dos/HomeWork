package generics;

import calculables.Calculable;

public interface TripleExpression<T> {
    Calculable<T> evaluate(Calculable<T> x, Calculable<T> y, Calculable<T> z);
}
