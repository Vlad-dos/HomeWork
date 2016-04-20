package calculables;

import java.math.BigInteger;

public class CalculableBigInteger implements Calculable<BigInteger> {
    BigInteger value;

    public BigInteger getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = BigInteger.valueOf(value);
    }

    public CalculableBigInteger() {
        value = BigInteger.ZERO;
    }

    public CalculableBigInteger(BigInteger value) {
        this.value = value;
    }

    public Calculable<BigInteger> add(Calculable<BigInteger> a) {
        return new CalculableBigInteger(value.add(a.getValue()));
    }

    public Calculable<BigInteger> negate() {
        return new CalculableBigInteger(value.negate());
    }

    public Calculable<BigInteger> multiply(Calculable<BigInteger> a) {
        return new CalculableBigInteger(value.multiply(a.getValue()));
    }

    public Calculable<BigInteger> divide(Calculable<BigInteger> a) {
        return new CalculableBigInteger(value.divide(a.getValue()));
    }

    public Calculable<BigInteger> subtract(Calculable<BigInteger> a) {
        return new CalculableBigInteger(value.subtract(a.getValue()));
    }

    public Calculable<BigInteger> mod(Calculable<BigInteger> a) {
        return new CalculableBigInteger(value.mod(a.getValue()));
    }

    public Calculable<BigInteger> abs() {
        return new CalculableBigInteger(value.abs());
    }

    public Calculable<BigInteger> square() {
        return new CalculableBigInteger(value.multiply(value));
    }

    public void parse(String a) throws NumberFormatException {
        value = new BigInteger(a);
    }
}