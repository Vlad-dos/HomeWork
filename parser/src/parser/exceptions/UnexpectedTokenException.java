package parser.exceptions;

public class UnexpectedTokenException extends ParserException {
    public char token;
    public int position;

    public UnexpectedTokenException(char token, int position) {
        super(token + " at " + position);
        this.token = token;
        this.position = position;
    }

    public UnexpectedTokenException() {
        super();
    }

    public UnexpectedTokenException(String message) {
        super(message);
    }
}
