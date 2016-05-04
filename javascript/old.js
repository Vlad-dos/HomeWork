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

function addDiff(expr, varName) {
    return new Add(expr[0].diff(varName), expr[1].diff(varName));
}
function subDiff(expr, varName) {
    return new Subtract(expr[0].diff(varName), expr[1].diff(varName));
}
function mulDiff(expr, varName) {
    return new Add(
        new Multiply(expr[0].diff(varName), expr[1]),
        new Multiply(expr[0], expr[1].diff(varName))
    );
}
function divDiff(expr, varName) {
    return new Divide(
        new Subtract(
            new Multiply(expr[0].diff(varName), expr[1]),
            new Multiply(expr[0], expr[1].diff(varName))
        ),
        new Multiply(expr[1], expr[1])
    );
}
function negDiff(expr, varName) {
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

function Variable(varName) {
    this.name = varName;
}
Variable.prototype.evaluate = function () {
    return arguments[Variable.variables[this.name]];
};
Variable.variables = {"x": 0, "y": 1, "z": 2};
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

function Operation(operation, name, diffFunc, args) {
    var expressions = Array.prototype.slice.call(args, 0);

    this.evaluate = function () {
        var args = arguments;
        return operation.apply(null, expressions.map(function (expr) {
            return expr.evaluate.apply(expr, args);
        }));
    };

    this.toString = function () {
        return expressions.reduce(function (ans, expr) {
                return ans + ' ' + expr.toString();
            }) + ' ' + name;
    };

    this.diff = function (varName) {
        return diffFunc(expressions, varName);
    };
}

function createOperation(func, funcName, diffFunc) {
    var Func = function () {
        Operation.call(this, func, funcName, diffFunc, arguments);
    };
    Func.size = func.length;
    return Func;
}

var Add = createOperation(add, '+', addDiff);
var Subtract = createOperation(subtract, '-', subDiff);
var Multiply = createOperation(multiply, '*', mulDiff);
var Divide = createOperation(divide, '/', divDiff);
var Sin = createOperation(Math.sin, 'sin', sinDiff);
var Cos = createOperation(Math.cos, 'cos', cosDiff);
var Negate = createOperation(negate, 'negate', negDiff);

var operations = {
    "+": Add,
    "-": Subtract,
    "*": Multiply,
    "/": Divide,
    "negate": Negate,
    "sin": Sin,
    "cos": Cos
};

function parse(text) {
    var stack = [];
    text.trim().split(" ").forEach(function (token) {
        if (token in Variable.variables) {
            stack.push(new Variable(token));
        } else if (token in operations) {
            var sz = operations[token].size;
            var expr = Object.create(operations[token].prototype);
            operations[token].apply(expr, stack.splice(-sz, sz));
            stack.push(expr);
        } else if (token) {
            stack.push(new Const(parseInt(token)));
        }
    });
    return stack[0];
}