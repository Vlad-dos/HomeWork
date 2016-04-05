package parser;

import parser.exceptions.ParserException;

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
        System.out.println(expression.evaluate(0, 0, 1));
    }
}
