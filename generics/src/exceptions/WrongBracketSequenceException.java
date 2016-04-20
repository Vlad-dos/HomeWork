package exceptions;

public class WrongBracketSequenceException extends ParserException {

    public WrongBracketSequenceException() {
        super();
    }

    public WrongBracketSequenceException(String message) {
        super(message);
    }
}
