package calculables;

import exceptions.OverflowException;

public class CalculableCheckedInteger implements Calculable<Integer> {
    Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public CalculableCheckedInteger() {
        value = 0;
    }

    public CalculableCheckedInteger(Integer value) {
        this.value = value;
    }

    public Calculable<Integer> add(Calculable<Integer> B) {
        int a = value;
        int b = B.getValue();
        int bMax = (a > 0 ? Integer.MAX_VALUE - a : Integer.MAX_VALUE);
        int bMin = (a < 0 ? Integer.MIN_VALUE - a : Integer.MIN_VALUE);
        if (!(bMin <= b && b <= bMax)) {
            throw new OverflowException("Add overflow", a, b);
        } else {
            return new CalculableCheckedInteger(value + b);
        }
    }

    public Calculable<Integer> negate() {
        if (value == Integer.MIN_VALUE) {
            throw new OverflowException("Negate overflow", value);
        } else {
            return new CalculableCheckedInteger(-value);
        }
    }

    public Calculable<Integer> multiply(Calculable<Integer> A) {
        int a = value;
        int b = A.getValue();
        if (a == 0) {
            return new CalculableCheckedInteger(value * A.getValue());
        }
        if (a == -1) {
            if (b == Integer.MIN_VALUE) {
                throw new OverflowException("Multiply overflow", a, b);
            } else {
                return new CalculableCheckedInteger(value * A.getValue());
            }
        }
        int bMax = Integer.MAX_VALUE / a;
        int bMin = Integer.MIN_VALUE / a;
        if (bMax < bMin) {
            int w = bMin;
            bMin = bMax;
            bMax = w;
        }
        if (!(bMin <= b && b <= bMax)) {
            throw new OverflowException("Multiply overflow", a, b);
        } else {
            return new CalculableCheckedInteger(value * A.getValue());
        }
    }

    public Calculable<Integer> divide(Calculable<Integer> a) {
        return new CalculableCheckedInteger(value / a.getValue());
    }

    public Calculable<Integer> subtract(Calculable<Integer> B) {
        int a = value;
        int b = B.getValue();
        int aMin = (b > 0 ? (Integer.MIN_VALUE + b) : Integer.MIN_VALUE);
        int aMax = (b < 0 ? (Integer.MAX_VALUE + b) : Integer.MAX_VALUE);
        if (!(aMin <= a && a <= aMax)) {
            throw new ArithmeticException("Subtract overflow");
        } else {
            return new CalculableCheckedInteger(value - B.getValue());
        }
    }

    public Calculable<Integer> mod(Calculable<Integer> a) {
        return new CalculableCheckedInteger(value % a.getValue());
    }

    public Calculable<Integer> abs() {
        if (value == Integer.MIN_VALUE) {
            throw new OverflowException("Abs overflow", value);
        } else {
            return new CalculableCheckedInteger(value > 0 ? value : -value);
        }
    }

    public Calculable<Integer> square() {
        return multiply(this);
    }

    public void parse(String a) throws NumberFormatException {
        value = Integer.parseInt(a);
    }
}