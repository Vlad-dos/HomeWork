package expression;

public class Variable implements Evaluable {
    private String name;

    Variable(String name) {
        this.name = name;
    }

    public double evaluate(double x) {
        return x;
    }
}
