package parser;

import parser.exceptions.NumberSizeException;
import parser.exceptions.ParserException;
import parser.exceptions.UnexpectedTokenException;
import parser.exceptions.WrongBracketSequenceException;

public class ExpressionParser implements Parser {
    private int position = 0;
    private int lastPosition = 0;
    private String text;

    private void resetToken() {
        position = lastPosition;
    }

    private boolean isDigit(char value) {
        return value >= '0' && value <= '9';
    }

    private boolean isLetter(char value) {
        return value >= 'a' && value <= 'z';
    }

    private TripleExpression getNextToken(boolean bracketExpected, boolean valueExpected) throws ParserException {
        lastPosition = position;
        char first = ' ';
        while (first == ' ' || first == '\t') {
            if (position >= text.length()) {
                if (!bracketExpected && !valueExpected) {
                    return null;
                } else {
                    throw new WrongBracketSequenceException();
                }
            } else {
                first = text.charAt(position++);
            }
        }
        if (valueExpected) {
            if (isDigit(first) || (position < text.length() && first == '-' && isDigit(text.charAt(position)))) {
                int beginIndex = position - 1;
                while (position < text.length() && isDigit(text.charAt(position))) {
                    position++;
                }
                try {
                    return new Const(Integer.parseInt(text.substring(beginIndex, position)));
                } catch (NumberFormatException e) {
                    throw new NumberSizeException(text.substring(beginIndex, position), position);
                }
            }
            if (isLetter(first)) {
                int beginIndex = position - 1;
                while (position < text.length() && isLetter(text.charAt(position))) {
                    position++;
                }
                String name = text.substring(beginIndex, position);
                switch (name) {
                    case "x":
                    case "y":
                    case "z":
                        return new Variable(name);
                    case "sqrt":
                        return new CheckedSqrt(getNextToken(bracketExpected, true));
                    case "abs":
                        return new CheckedAbs(getNextToken(bracketExpected, true));
                    default:
                        throw new UnexpectedTokenException(first, position);
                }
            }
            switch (first) {
                case '-':
                    return new CheckedNegate(getNextToken(bracketExpected, true));
                case '(':
                    return parseBinaryOperation(true, 0);
                default:
                    throw new UnexpectedTokenException(first, position);
            }
        } else {
            switch (first) {
                case '*':
                    if (text.charAt(position) == '*') {
                        position++;
                        return new CheckedPow();
                    }
                    return new CheckedMultiply();
                case '/':
                    if (text.charAt(position) == '/') {
                        position++;
                        return new CheckedLogarithm();
                    }
                    return new CheckedDivide();
                case '+':
                    return new CheckedAdd();
                case '-':
                    return new CheckedSubtract();
                case ')':
                    if (bracketExpected) {
                        return null;
                    } else {
                        throw new WrongBracketSequenceException();
                    }
                default:
                    throw new UnexpectedTokenException(first, position);
            }
        }
    }

    CheckedBinaryOperation[][] levels = new CheckedBinaryOperation[][]{
            {new CheckedAdd(), new CheckedSubtract()},
            {new CheckedDivide(), new CheckedMultiply()},
            {new CheckedLogarithm(), new CheckedPow()}
    };

    private TripleExpression parseBinaryOperation(boolean bracketExpected, int level) throws ParserException {
        if (level >= levels.length) {
            return getNextToken(bracketExpected, true);
        }
        TripleExpression first = parseBinaryOperation(bracketExpected, level + 1);
        TripleExpression temp = getNextToken(bracketExpected, false);
        while (temp != null) {
            CheckedBinaryOperation operation = null;
            for (CheckedBinaryOperation i : levels[level]) {
                if (temp.getClass() == i.getClass()) {
                    operation = (CheckedBinaryOperation) temp;
                }
            }
            if (operation == null) {
                if (level == 0) {
                    throw new UnexpectedTokenException(text.charAt(position - 1), position - 1);
                } else {
                    resetToken();
                    return first;
                }
            }
            TripleExpression second = parseBinaryOperation(bracketExpected, level + 1);
            operation.setFirst(first);
            operation.setSecond(second);
            first = operation;
            temp = getNextToken(bracketExpected, false);
        }
        if (level != 0) resetToken();
        return first;
    }

    public TripleExpression parse(String expression) throws ParserException {
        text = expression;
        position = 0;
        TripleExpression result = parseBinaryOperation(false, 0);
        if (position < text.length()) {
            throw new WrongBracketSequenceException();
        }
        return result;
    }
}
