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
    return new Add(expr[0].diff(varName), expr[1].diff(varName));
}
function subDiff(expr, varName)  {
    return new Subtract(expr[0].diff(varName), expr[1].diff(varName));
}
function mulDiff(expr, varName)  {
    return new Add(
        new Multiply(expr[0].diff(varName), expr[1]),
        new Multiply(expr[0], expr[1].diff(varName))
    );
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
    return new Negate(expr[0].diff(varName));
}

function sinDiff(expr, varName) {
    return new Multiply(new Cos(expr[0]), expr[0].diff(varName));
}

function cosDiff(expr, varName) {
    return new Multiply(new Negate(new Sin(expr[0])), expr[0].diff(varName));
}

function Const(constValue) {
    this.value = constValue;
}
Const.prototype.evaluate = function () {
    return this.value;
};

Const.prototype.toString = function () {
    return this.value.toString();
};

Const.prototype.diff = function () {
    return new Const(0);
};

Variable.variables = {"x": 0, "y": 1, "z": 2};
function Variable(varName) {
    this.name = varName;
}
Variable.prototype.evaluate = function () {
    return arguments[Variable.variables[this.name]];
};

Variable.prototype.toString = function () {
    return this.name;
};

Variable.prototype.diff = function (varName) {
    if (this.name === varName) {
        return new Const(1);
    } else {
        return new Const(0);
    }
};

var depth = "";
function Operation(func, funcName, diffFunc, args) {
    var expressions = [];
    for (var i = 0; i < args.length; i++) {
        expressions.push(args[i]);
    }

    var operation = func;
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
    };
}

function Add() {
    Operation.call(this, add, '+', addDiff, arguments);
}

function Subtract() {
    Operation.call(this, subtract, '-', subDiff, arguments);
}

function Multiply() {
    Operation.call(this, multiply, '*', mulDiff, arguments);
}

function Divide() {
    Operation.call(this, divide, '/', divDiff, arguments);
}

function Sin() {
    Operation.call(this, Math.sin, 'sin', sinDiff, arguments);
}

function Cos() {
    Operation.call(this, Math.cos, 'cos', cosDiff, arguments);
}

function Negate() {
    Operation.call(this, negate, 'negate', negDiff, arguments);
}

var operations = {
    "+": {
        Func: Add,
        size:2
    },
    "-": {
        Func: Subtract,
        size:2
    },
    "*": {
        Func: Multiply,
        size:2
    },
    "/": {
        Func: Divide,
        size:2
    },
    "negate": {
        Func: Negate,
        size:1
    },
    "sin": {
        Func: Sin,
        size:1
    },
    "cos": {
        Func: Cos,
        size:1
    }
};

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
            var expr = Object.create(operations[token].Func.prototype);
            operations[token].func.apply(expr, args);
            stack.push(expr);
        } else if (token) {
            stack.push(new Const(parseInt(token)));
        }
    });
    return stack[0];
}