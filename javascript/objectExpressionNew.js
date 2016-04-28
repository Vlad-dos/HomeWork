"use strict";

function add(a, b) {
    return a + b;
}

function subtract(a, b) {
    return a - b;
}

function multiply(a, b) {
    return a * b;
}

function divide(a, b) {
    return a / b;
}

function negate(a) {
    return -a;
}

function addDiff(expr, varName)  {
    return new Add(expr[0].diff(varName), expr[1].diff(varName))
}
function subDiff(expr, varName)  {
    return new Subtract(expr[0].diff(varName), expr[1].diff(varName))
}
function mulDiff(expr, varName)  {
    return new Add(
        new Multiply(expr[0].diff(varName), expr[1]),
        new Multiply(expr[0], expr[1].diff(varName))
    )
}

function divDiff(expr, varName)  {
    return new Divide(
        new Subtract(
            new Multiply(expr[0].diff(varName), expr[1]),
            new Multiply(expr[0], expr[1].diff(varName))
        ),
        new Multiply(expr[1],expr[1])
    );
}

function negDiff(expr, varName)  {
    return new Negate(expr[0].diff(varName))
}

function sinDiff(expr, varName) {
    return new Multiply(new Cos(expr[0]), expr[0].diff(varName));
}

function cosDiff(expr, varName) {
    return new Multiply(new Negate(new Sin(expr[0])), expr[0].diff(varName));
}

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

function Operation(func, funcName, diffFunc, args) {
    var expressions = [];
    for (var i = 0; i < args.length; i++) {
        expressions.push(args[i]);
    }

    var operation = func
    var name = funcName;

    this.evaluate = function () {
        var argsApply = [];
        for (var i = 0; i < expressions.length; i++) {
            argsApply.push(expressions[i].evaluate.apply(expressions[i], arguments));
        }
        return operation.apply(null, argsApply);
    };

    this.toString = function () {
        var ans = "";
        expressions.forEach(function (expr) {
            ans += expr.toString() + " ";
        });
        return ans + name;
    };

    this.diff = function(varName) {
        return diffFunc(expressions, varName);
    }

    this.simplify = function () {
        var allConst = true;
        var newExpr = [];
        for (var i = 0; i < expressions.length; i++) {
            var tmp = expressions[i].simplify();
            newExpr.push(tmp);
            if (!(newExpr[i] instanceof Const)) {
                allConst = false;
            }
        }
        if (allConst) {
            return new Const(this.evaluate());
        } else {
            return this.localSimplify(newExpr);
        }
    };
}

function Add() {
    Operation.call(this, add, '+', addDiff, arguments);

    this.localSimplify = function(expr) {
        if (expr[0] instanceof Const && expr[0].evaluate() === 0) {
            return expr[1];
        } else if (expr[1] instanceof Const && expr[1].evaluate() === 0) {
            return expr[0];
        } else {
            return this;
        }
    }
}

function Subtract() {
    Operation.call(this, subtract, '-', subDiff, arguments);

    this.localSimplify = function(expr) {
        if (expr[0] instanceof Const && expr[0].evaluate() === 0) {
            return new Negate(expr[1]);
        } else if (expr[1] instanceof Const && expr[1].evaluate() === 0) {
            return expr[0];
        } else {
            return this;
        }
    }
}

function Multiply() {
    Operation.call(this, multiply, '*', mulDiff, arguments);

    this.localSimplify = function(expr) {
        if (expr[0] instanceof Const && expr[0].evaluate() === 1) {
            return expr[1];
        } else if (expr[1] instanceof Const && expr[1].evaluate() === 1) {
            return expr[0];
        } else if ((expr[1] instanceof Const && expr[1].evaluate() === 0) ||
                   (expr[0] instanceof Const && expr[0].evaluate() === 0)) {
            return new Const(0);
        } else {
            return this;
        }
    }
}

function Divide() {
    Operation.call(this, divide, '/', divDiff, arguments);

    this.localSimplify = function(expr) {
        if (expr[0] instanceof Const && expr[0].evaluate() == 0) {
            return new Const(0);
        } else if (expr[1] instanceof Const && expr[1].evaluate() == 1) {
            return expr[0];
        } else {
            return this;
        }
    }
}

function Sin() {
    Operation.call(this, Math.sin, 'sin', sinDiff, arguments);

    this.localSimplify = function() {
        return this;
    }
}

function Cos() {
    Operation.call(this, Math.cos, 'cos', cosDiff, arguments);

    this.localSimplify = function() {
        return this;
    }
}

function Negate() {
    Operation.call(this, negate, 'negate', negDiff, arguments);

    this.localSimplify = function(expr) {
        return this;
    }
}

var operations = {
    "+": {
        func:Add,
        size:2
    },
    "-": {
        func:Subtract,
        size:2
    },
    "*": {
        func:Multiply,
        size:2
    },
    "/": {
        func:Divide,
        size:2
    },
    "negate": {
        func:Negate,
        size:1
    },
    "sin": {
        func:Sin,
        size:1
    },
    "cos": {
        func:Cos,
        size:1
    }
}

function parse(text) {
    var stack = [];
    text.trim().split(" ").forEach(function (token) {
        if (token in Variable.variables) {
            stack.push(new Variable(token));
        } else if (token in operations) {
            var args = [];
            for (var i = 0; i < operations[token].size; i++) {
                args.push(stack.pop());
            }
            args.reverse();
            var expr = Object.create(operations[token].func.prototype);
            operations[token].func.apply(expr, args);
            stack.push(expr);
        }
        else if (token) {
            stack.push(new Const(parseInt(token)));
        }
    });
    return stack[0];
}