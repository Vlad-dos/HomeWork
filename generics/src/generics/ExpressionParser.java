package generics;

import calculables.Calculable;
import exceptions.NumberSizeException;
import exceptions.ParserException;
import exceptions.UnexpectedTokenException;
import exceptions.WrongBracketSequenceException;
import operations.*;

@SuppressWarnings("Duplicates")
public class ExpressionParser<T> {
    private int position = 0;
    private int lastPosition = 0;
    private String text;
    CalculableCreator<T> creator;

    public ExpressionParser(CalculableCreator<T> creator) {
        this.creator = creator;
    }

    private void resetToken() {
        position = lastPosition;
    }

    private boolean isDigit(char value) {
        return value >= '0' && value <= '9';
    }

    private boolean isLetter(char value) {
        return value >= 'a' && value <= 'z';
    }

    private TripleExpression<T> getNextToken(boolean bracketExpected, boolean valueExpected) throws ParserException {
        lastPosition = position;
        char first = ' ';
        while (first == ' ' || first == '\t') {
            if (position >= text.length()) {
                if (!bracketExpected && !valueExpected) {
                    return null;
                } else {
                    throw new WrongBracketSequenceException("at " + position);
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
                Calculable<T> temp = creator.create();
                try {
                    temp.parse(text.substring(beginIndex, position));
                } catch (NumberFormatException e) {
                    throw new NumberSizeException(text.substring(beginIndex, position), position);
                }
                return new Const<>(temp);
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
                        return new Variable<>(name);
                    case "abs":
                        return new Abs<>(getNextToken(bracketExpected, true));
                    case "square":
                        return new Square<>(getNextToken(bracketExpected, true));
                    default:
                        throw new UnexpectedTokenException(first, position);
                }
            }
            switch (first) {
                case '-':
                    return new Negate<>(getNextToken(bracketExpected, true));
                case '(':
                    return parseBinaryOperation(true, 0);
                default:
                    throw new UnexpectedTokenException(first, position);
            }
        } else {
            int beginIndex = position - 1;
            while (position < text.length() && isLetter(text.charAt(position))) {
                position++;
            }
            String name = text.substring(beginIndex, position);
            switch (name) {
                case "*":
                    return new Multiply<>();
                case "/":
                    return new Divide<>();
                case "+":
                    return new Add<>();
                case "mod":
                    return new Mod<>();
                case "-":
                    return new Subtract<>();
                case ")":
                    if (bracketExpected) {
                        return null;
                    } else {
                        throw new WrongBracketSequenceException("at " + position);
                    }
                default:
                    throw new UnexpectedTokenException(first, position);
            }
        }
    }

    BinaryOperation[][] levels = new BinaryOperation[][]{
            {new Add<T>(), new Subtract<T>()},
            {new Divide<T>(), new Multiply<T>(), new Mod<T>()},
    };

    private TripleExpression<T> parseBinaryOperation(boolean bracketExpected, int level) throws ParserException {
        if (level >= levels.length) {
            return getNextToken(bracketExpected, true);
        }
        TripleExpression<T> first = parseBinaryOperation(bracketExpected, level + 1);
        TripleExpression<T> temp = getNextToken(bracketExpected, false);
        while (temp != null) {
            BinaryOperation<T> operation = null;
            for (BinaryOperation i : levels[level]) {
                if (temp.getClass() == i.getClass()) {
                    operation = (BinaryOperation<T>) temp;
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
            TripleExpression<T> second = parseBinaryOperation(bracketExpected, level + 1);
            operation.setFirst(first);
            operation.setSecond(second);
            first = operation;
            temp = getNextToken(bracketExpected, false);
        }
        if (level != 0) resetToken();
        return first;
    }

    public TripleExpression<T> parse(String expression) throws ParserException {
        text = expression;
        position = 0;
        TripleExpression<T> result = parseBinaryOperation(false, 0);
        if (position < text.length()) {
            throw new WrongBracketSequenceException("at " + position);
        }
        return result;
    }
}

