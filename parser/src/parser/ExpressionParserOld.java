package parser;

import parser.exceptions.ParserException;
import parser.exceptions.UnexpectedTokenException;

public class ExpressionParserOld implements Parser {

    private int position = 0;
    private String text;
    private Token lastToken = new Token(true);

    private boolean isDigit(char value) {
        return value >= '0' && value <= '9';
    }

    private class Token {
        public boolean isConst;
        public boolean isCloseBracket;
        public boolean isOperation;
        public boolean isVariable;
        public boolean isNegate;
        public boolean isEnd;
        public boolean isOpenBracket;
        public boolean isBegin;

        public int number;
        public char value;

        public char getOperation() {
            return value;
        }

        Token() {
        }

        Token(boolean begin) {
            isBegin = begin;
        }

        public boolean isBeginState() {
            return (isNegate || isBegin || isOpenBracket || isOperation);
        }
    }

    private Token getNextToken() throws ParserException {
        Token token = new Token();
        char first = ' ';
        boolean correct = false;
        while (first == ' ' || first == '\t') {
            if (position >= text.length()) {
                correct = !lastToken.isBeginState();
                token.isEnd = correct;
                first = '\n';
            } else {
                first = text.charAt(position++);
            }
        }
        if (lastToken.isBeginState()) {
            correct = true;
            if (isDigit(first) || (position + 1 < text.length() && first == '-' && isDigit(text.charAt(position + 1)))) {
                token.isConst = true;
                int beginIndex = position - 1;
                while (position < text.length() && isDigit(text.charAt(position))) {
                    position++;
                }
                try {
                    token.number = Integer.parseInt(text.substring(beginIndex, position));
                } catch (NumberFormatException e) {
                    throw new ParserException("Not an int number", e);
                }
            } else switch (first) {
                case 'x':
                case 'y':
                case 'z':
                    token.isVariable = true;
                    token.value = first;
                    break;
                case '-':
                    token.isNegate = true;
                    break;
                case '(':
                    token.isOpenBracket = true;
                    break;
                default:
                    correct = false;
            }
        } else {
            if (first == '+' || first == '-' || first == '*' || first == '/') {
                token.isOperation = true;
                token.value = first;
                correct = true;
            }
            if (first == ')') {
                token.isCloseBracket = true;
                correct = true;
            }
        }
        if (!correct) {
            throw new UnexpectedTokenException(first, position);
        } else {
            lastToken = token;
            return token;
        }
    }

    private TripleExpression processValues(Token token, boolean bracketExpected) throws ParserException {
        TripleExpression expression = null;
        if (token.isConst) {
            expression = new Const(token.number);
        } else if (token.isVariable) {
            expression = new Variable(String.valueOf(token.value));
        } else if (token.isNegate) {
            Token token2 = getNextToken();
            expression = processValues(token2, bracketExpected);
            expression = new CheckedNegate(expression);
        } else if (token.isOpenBracket) {
            expression = process(true);
        }
        if (expression == null) {
            throw new UnexpectedTokenException();
        }
        return expression;
    }

    private TripleExpression process(boolean bracketExpected) throws ParserException {
        Token token = getNextToken();
        TripleExpression first = processValues(token, bracketExpected);
        TripleExpression second;
        token = getNextToken();

        if (token.isOperation) {
            if (token.value == '*' || token.value == '/') {
                while (token.isOperation && (token.value == '*' || token.value == '/')) {
                    Token tmpToken = getNextToken();
                    second = processValues(tmpToken, bracketExpected);
                    if (token.value == '*') {
                        first = new CheckedMultiply(first, second);
                    } else {
                        first = new CheckedDivide(first, second);
                    }
                    token = getNextToken();
                }
            }
        }
        if (token.isOperation) {
            second = process(bracketExpected);
            if (token.value == '-') {
                return new CheckedSubtract(first, second);
            }
            if (token.value == '+') {
                return new CheckedAdd(first, second);
            }
        }
        if ((token.isEnd && !bracketExpected) || (token.isCloseBracket && bracketExpected)) {
            return first;
        }
        throw new ParserException("Wrong bracket sequence");
    }

    public TripleExpression parse(String expression) throws ParserException {
        text = expression;
        position = 0;
        lastToken = new Token(true);
        TripleExpression result = process(false);
        if (position < text.length()) {
            throw new ParserException("Wrong bracket sequence");
        }
        return result;
    }
}
