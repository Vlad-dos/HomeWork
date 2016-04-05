package parser.exceptions;

public class OverflowException extends ArithmeticException {
    int a, b;

    public OverflowException() {
        super();
    }

    public OverflowException(String message, int a, int b) {
        super(message);
        this.a = a;
        this.b = b;
    }

    public OverflowException(String message, int a) {
        super(message);
        this.a = a;
    }
}
