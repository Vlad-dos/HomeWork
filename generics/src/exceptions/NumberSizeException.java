package exceptions;

public class NumberSizeException extends ParserException {
    public String number;
    public int position;

    public NumberSizeException(String number, int position) {
        super();
        this.number = number;
        this.position = position;
    }

    public NumberSizeException() {
        super();
    }

    public NumberSizeException(String message) {
        super(message);
    }
}
