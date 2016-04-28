"use strict";

function Const(constValue) {
    var value = constValue;

    this.evaluate = function () {
        return value;
    };

    this.toString = function () {
        return value.toString();
    };

    this.diff = function () {
        return new Const(0);
    };

    this.simplify = function () {
        return this;
    };
}

Variable.variables = {"x": 0, "y": 1, "z": 2};
function Variable(varName) {
    var name = varName;

    this.evaluate = function () {
        return arguments[Variable.variables[name]];
    };

    this.toString = function () {
        return name;
    };

    this.diff = function (varName) {
        if (name === varName) {
            return new Const(1);
        } else {
            return new Const(0);
        }
    };

    this.simplify = function () {
        return this;
    };
}

var deapth = "";

function Operation(func, funcName) {
    this.expressions = [];
    for (var i = 2; i < arguments.length; i++) {
        this.expressions.push(arguments[i]);
    }

    var operation = func;
    var name = funcName;

    this.evaluate = function () {
        var argsApply = [];
        for (var i = 0; i < this.expressions.length; i++) {
            argsApply.push(this.expressions[i].evaluate.apply(this.expressions[i], arguments));
        }
        return operation.apply(null, argsApply);
    };

    this.toString = function () {
        var ans = "";
        this.expressions.forEach(function (expr) {
            ans += expr.toString() + " ";
        });
        return ans + name;
    };

    this.localSimplify = function (newExpr) {
        var ans = Operation;
        (Operation.apply(ans, newExpr));
        return ans;
    };

    this.simplify = function () {
        println("\n\n" + deapth + "simplify: " + name + ' ' + this.expressions);
        var allConst = true;
        var newExpr = [];
        for (var i = 0; i < this.expressions.length; i++) {
            deapth += "  ";
            var tmp = this.expressions[i].simplify();
            deapth = deapth.slice(0, -2);
            newExpr.push(tmp);
            if (!(newExpr[i + 2] instanceof Const)) {
                allConst = false;
            }
        }
        println(deapth + "after " + newExpr.length + " : " + newExpr);
        if (allConst) {
            println(deapth + "result = " + new Const(this.evaluate()) + "\n");
            return new Const(this.evaluate());
        } else {
            var ans = this.constructor;
            (Operation.apply(ans, newExpr));
            println(deapth + "result = " + this.localSimplify.call(ans) + "\n");
            return this.localSimplify.call(ans);
        }
    };

    this.reverse = function () {
        this.expressions = this.expressions.reverse();
    };
}

Add.prototype = Object.create(Operation.prototype);
function Add(first, second) {
    Operation.call(this, function (a, b) {
        return a + b;
    }, "+", first, second);

    this.diff = function (varName) {
        return new Add(this.expressions[0].diff(varName), this.expressions[1].diff(varName));
    };

    this.localSimplify = function () {
        if (this.expressions[0] instanceof Const && this.expressions[0].evaluate() === 0) {
            return this.expressions[1];
        } else if (this.expressions[1] instanceof Const && this.expressions[1].evaluate() === 0) {
            return this.expressions[0];
        } else {
            return this;
        }
    };
}

Subtract.prototype = Object.create(Operation.prototype);
function Subtract(first, second) {
    Operation.call(this, function (a, b) {
        return a - b;
    }, "-", first, second);

    this.diff = function (varName) {
        return new Subtract(this.expressions[0].diff(varName), this.expressions[1].diff(varName));
    };

    this.localSimplify = function () {
        println("Subtract.localSimplify: " + this.expressions[0], this.expressions[1]);
        if (this.expressions[1] instanceof Const && this.expressions[1].evaluate() === 0) {
            return this.expressions[0];
        } else if (this.expressions[0] instanceof Const && this.expressions[0].evaluate() === 0) {
            return (new Negate(this.expressions[1])).localSimplify();
        } else {
            return this;
        }
    };
}

Multiply.prototype = Object.create(Operation.prototype);
function Multiply(first, second) {
    Operation.call(this, function (a, b) {
        return a * b;
    }, "*", first, second);

    this.diff = function (varName) {
        return new Add(new Multiply(this.expressions[0].diff(varName), this.expressions[1]), new Multiply(this.expressions[1].diff(varName), this.expressions[0]));
    };

    this.localSimplify = function () {
        if (this.expressions[0] instanceof Const && this.expressions[0].evaluate() === 1) {
            return this.expressions[1];
        } else if (this.expressions[1] instanceof Const && this.expressions[1].evaluate() === 1) {
            return this.expressions[0];
        } else if ((this.expressions[0] instanceof Const && this.expressions[0].evaluate() === 0) || (this.expressions[1] instanceof Const && this.expressions[1].evaluate() === 0)) {
            return new Const(0);
        } else {
            return this;
        }
    };
}

Divide.prototype = Object.create(Operation.prototype);
function Divide(first, second) {
    Operation.call(this, function (a, b) {
        return a / b;
    }, "/", first, second);

    this.diff = function (varName) {
        return new Divide(new Add(new Multiply(this.expressions[0].diff(varName), this.expressions[1]), new Multiply(this.expressions[1].diff(varName), this.expressions[0])), new Multiply(this.expressions[1], this.expressions[1]));
    };

    this.localSimplify = function () {
        if (this.expressions[1] instanceof Const && this.expressions[1].evaluate() === 1) {
            return this.expressions[0];
        } else if ((this.expressions[0] instanceof Const && this.expressions[0].evaluate() === 0) && (this.expressions[1] instanceof Const && this.expressions[1].evaluate() !== 0)) {
            return new Const(0);
        } else {
            return this;
        }
    };
}

Negate.prototype = Object.create(Operation.prototype);
function Negate(expr) {
    Operation.call(this, function (a, b) {
        return a / b;
    }, "negate", expr);

    this.diff = function (varName) {
        return new Negate(expr.diff(varName));
    };

    this.localSimplify = function () {
        if (expr instanceof Subtract) {
            expr.reverse();
            return expr;
        } else {
            return this;
        }
    };
}