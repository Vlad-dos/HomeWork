package parser;

public interface Parser {
    TripleExpression parse(String expression) throws ParserException;
}