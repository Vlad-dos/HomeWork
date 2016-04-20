package exceptions;

public class OverflowException extends ArithmeticException {
    int a, b;

    public OverflowException() {
        super();
    }

    public OverflowException(String message, int a, int b) {
        super(message + " at " + a + " " + b);
        this.a = a;
        this.b = b;
    }

    public OverflowException(String message, int a) {
        super(message + " at " + a);
        this.a = a;
    }
}
