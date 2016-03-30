package parser;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(
                    new CheckedAdd(
                            new Const(Integer.MAX_VALUE / 2 + 1),
                            new Const(Integer.MAX_VALUE / 2)
                    ).evaluate(0, 0, 0)
            );
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(
                    new CheckedSubtract(
                            new Const(Integer.MIN_VALUE),
                            new Const(0)
                    ).evaluate(0, 0, 0)
            );
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(
                    new CheckedMultiply(
                            new Const(Integer.MIN_VALUE),
                            new Const(1)
                    ).evaluate(0, 0, 0)
            );
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(
                    new CheckedDivide(
                            new Const(-2),
                            new Const(1)
                    ).evaluate(0, 0, 0)
            );
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }
}
