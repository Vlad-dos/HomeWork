package generics;

import calculables.Calculable;

public class Variable<T> implements TripleExpression<T> {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public Calculable<T> evaluate(Calculable<T> x, Calculable<T> y, Calculable<T> z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                return null;
        }
    }
}
