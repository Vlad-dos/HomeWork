package calculables;

public class CalculableDouble implements Calculable<Double> {
    Double value;

    public Double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = (double) value;
    }

    public CalculableDouble() {
        value = 0D;
    }

    public CalculableDouble(Double value) {
        this.value = value;
    }

    public Calculable<Double> add(Calculable<Double> a) {
        return new CalculableDouble(value + a.getValue());
    }

    public Calculable<Double> negate() {
        return new CalculableDouble(-value);
    }

    public Calculable<Double> multiply(Calculable<Double> a) {
        return new CalculableDouble(value * a.getValue());
    }

    public Calculable<Double> divide(Calculable<Double> a) {
        return new CalculableDouble(value / a.getValue());
    }

    public Calculable<Double> subtract(Calculable<Double> a) {
        return new CalculableDouble(value - a.getValue());
    }

    public Calculable<Double> mod(Calculable<Double> a) {
        return new CalculableDouble(value % a.getValue());
    }

    public Calculable<Double> abs() {
        return new CalculableDouble(value > 0 ? value : -value);
    }

    public Calculable<Double> square() {
        return new CalculableDouble(value * value);
    }

    public void parse(String a) throws NumberFormatException {
        value = Double.parseDouble(a);
    }
}