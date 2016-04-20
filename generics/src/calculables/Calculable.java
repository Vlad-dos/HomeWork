package calculables;

public interface Calculable<T> {
    T getValue();

    void setValue(int value);

    Calculable<T> add(Calculable<T> a);

    Calculable<T> multiply(Calculable<T> a);

    Calculable<T> divide(Calculable<T> a);

    Calculable<T> mod(Calculable<T> a);

    Calculable<T> subtract(Calculable<T> a);

    Calculable<T> abs();

    Calculable<T> square();

    Calculable<T> negate();

    void parse(String a) throws NumberFormatException;
}
