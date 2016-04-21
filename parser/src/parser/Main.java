package parser;

import parser.exceptions.NumberSizeException;
import parser.exceptions.ParserException;
import parser.exceptions.UnexpectedTokenException;
import parser.exceptions.WrongBracketSequenceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        System.out.println(new CheckedLogarithm(new Const(9), new Const(2)).evaluate(0, 0, 0));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String text;
        try {
            text = reader.readLine();
        } catch (IOException e) {
            System.out.println("Can't read expression.");
            return;
        }
        TripleExpression expression;
        try {
            expression = (new ExpressionParser()).parse(text);
        } catch (UnexpectedTokenException e) {
            System.out.println("Unexpected token: " + e.token + " at " + e.position);
            return;
        } catch (NumberSizeException e) {
            System.out.println("Number size too big: " + e.number);
            return;
        } catch (WrongBracketSequenceException e) {
            System.out.println("Wrong Bracket Sequence");
            return;
        } catch (ParserException e) {
            e.printStackTrace();
            return;
        }
        try {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        } catch (IOException e) {
            System.out.println("Can't read variables.");
            return;
        }
        try {
            System.out.println(expression.evaluate(0, 0, 1));
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }
}
