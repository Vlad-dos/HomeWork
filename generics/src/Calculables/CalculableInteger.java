package calculables;

public class CalculableInteger implements Calculable<Integer> {
    Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public CalculableInteger() {
        value = 0;
    }

    public CalculableInteger(Integer value) {
        this.value = value;
    }

    public Calculable<Integer> add(Calculable<Integer> a) {
        return new CalculableInteger(value + a.getValue());
    }

    public Calculable<Integer> negate() {
        return new CalculableInteger(-value);
    }

    public Calculable<Integer> multiply(Calculable<Integer> a) {
        return new CalculableInteger(value * a.getValue());
    }

    public Calculable<Integer> divide(Calculable<Integer> a) {
        return new CalculableInteger(value / a.getValue());
    }

    public Calculable<Integer> subtract(Calculable<Integer> a) {
        return new CalculableInteger(value - a.getValue());
    }

    public Calculable<Integer> mod(Calculable<Integer> a) {
        return new CalculableInteger(value % a.getValue());
    }

    public Calculable<Integer> abs() {
        return new CalculableInteger(value > 0 ? value : -value);
    }

    public Calculable<Integer> square() {
        return new CalculableInteger(value * value);
    }

    public void parse(String a) throws NumberFormatException {
        value = Integer.parseInt(a);
    }
}