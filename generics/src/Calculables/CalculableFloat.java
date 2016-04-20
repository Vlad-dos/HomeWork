package calculables;

public class CalculableFloat implements Calculable<Float> {
    Float value;

    public Float getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = (float) value;
    }

    public CalculableFloat() {
        value = 0F;
    }

    public CalculableFloat(Float value) {
        this.value = value;
    }

    public Calculable<Float> add(Calculable<Float> a) {
        return new CalculableFloat(value + a.getValue());
    }

    public Calculable<Float> negate() {
        return new CalculableFloat(-value);
    }

    public Calculable<Float> multiply(Calculable<Float> a) {
        return new CalculableFloat(value * a.getValue());
    }

    public Calculable<Float> divide(Calculable<Float> a) {
        return new CalculableFloat(value / a.getValue());
    }

    public Calculable<Float> subtract(Calculable<Float> a) {
        return new CalculableFloat(value - a.getValue());
    }

    public Calculable<Float> mod(Calculable<Float> a) {
        return new CalculableFloat(value % a.getValue());
    }

    public Calculable<Float> abs() {
        if (value == -0) {
            return new CalculableFloat(0F);
        }
        return new CalculableFloat(value > 0 ? value : -value);
    }

    public Calculable<Float> square() {
        return new CalculableFloat(value * value);
    }

    public void parse(String a) throws NumberFormatException {
        value = Float.parseFloat(a);
    }
}
