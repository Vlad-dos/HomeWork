package parser;

import parser.exceptions.ParserException;

public interface Parser {
    TripleExpression parse(String expression) throws ParserException;
}