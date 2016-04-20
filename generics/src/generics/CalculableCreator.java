package generics;

import calculables.Calculable;

public interface CalculableCreator<T> {
    Calculable<T> create();
}
