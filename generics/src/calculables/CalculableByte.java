package calculables;

public class CalculableByte implements Calculable<Byte> {
    Byte value;

    public Byte getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = ((byte) value);
    }

    public CalculableByte() {
        value = 0;
    }

    public CalculableByte(Byte value) {
        this.value = value;
    }

    public Calculable<Byte> add(Calculable<Byte> a) {
        return new CalculableByte((byte) (value + a.getValue()));
    }

    public Calculable<Byte> negate() {
        return new CalculableByte((byte) -value);
    }

    public Calculable<Byte> multiply(Calculable<Byte> a) {
        return new CalculableByte((byte) (value * a.getValue()));
    }

    public Calculable<Byte> divide(Calculable<Byte> a) {
        return new CalculableByte((byte) (value / a.getValue()));
    }

    public Calculable<Byte> subtract(Calculable<Byte> a) {
        return new CalculableByte((byte) (value - a.getValue()));
    }

    public Calculable<Byte> mod(Calculable<Byte> a) {
        return new CalculableByte((byte) (value % a.getValue()));
    }

    public Calculable<Byte> abs() {
        return new CalculableByte(value > 0 ? value : (byte) -value);
    }

    public Calculable<Byte> square() {
        return new CalculableByte((byte) (value * value));
    }

    public void parse(String a) throws NumberFormatException {
        value = Byte.parseByte(a);
    }
}