package expression;

public class Variable implements AllExpression {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public double evaluate(double x) {
        return x;
    }

    public int evaluate(int x) {
        return evaluate(x, 0, 0);
    }

    public int evaluate(int x, int y, int z) {
        if (name.equals("x")) {
            return x;
        }
        if (name.equals("y")) {
            return y;
        }
        if (name.equals("z")) {
            return z;
        }
        return 0;
    }
}
