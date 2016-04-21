package parser.exceptions;

public class NumberSizeException extends ParserException {
    public String number;
    public int position;

    public NumberSizeException(String number, int position) {
        super("number " + number + " at " + position + " too big");
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
