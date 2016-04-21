"use strict";

function cnst(value) {
    return function (x, y, z) {
        return value;
    };
}

var variables = ["x", "y", "z"];

function variable(name) {
    return function () {
        for (var i = 0; i < variables.length; i++) {
            if (name === variables[i]) {
                return arguments[i];
            }
        }
    };
}

function binaryOperation(operation) {
    return function (a, b) {
        return function (x, y, z) {
            return operation(a(x, y, z), b(x, y, z));
        };
    };
}

function unaryOperation(operation) {
    return function (a) {
        return function (x, y, z) {
            return operation(a(x, y, z));
        };
    };
}

/*
var add = binaryOperation((a, b) => a + b);
var multiply = binaryOperation((a, b) => a * b);
var subtract = binaryOperation((a, b) => a - b);
var divide = binaryOperation((a, b) => a / b);
var mod = binaryOperation((a, b) => a % b);
var power = binaryOperation((a, b) => Math.pow(a, b));

var negate = unaryOperation((a) => -a);
var log = unaryOperation((a) => Math.log(a));
var abs = unaryOperation((a) => Math.abs(a));
*/

var add = binaryOperation(function (a, b) {
    return a + b;
});
var multiply = binaryOperation(function (a, b) {
    return a * b;
});
var subtract = binaryOperation(function (a, b) {
    return a - b;
});
var divide = binaryOperation(function (a, b) {
    return a / b;
});
var mod = binaryOperation(function (a, b) {
    return a % b;
});
var power = binaryOperation(function (a, b) {
    return Math.pow(a, b);
});


var negate = unaryOperation(function (a) {
    return -a;
});
var abs = unaryOperation(function (a) {
    return Math.abs(a);
});
var log = unaryOperation(function (a) {
    return Math.log(a);
});

function parse(text) {
    var tokens = text.split(" ");
    var stack = [];
    tokens.forEach(function (token) {
        if (token[0] >= '0' && token[0] <= '9' || (token[0] === '-' && token[1] >= '0' && token[1] <= '9')) {
            stack.push(cnst(parseInt(token)));
        } else if (variables.indexOf(token) != -1) {
            stack.push(variable(token));
        } else {
            var unary = function(operation) {
                var tmp = stack.pop();
                stack.push(operation(tmp));
            }
            var binary = function(operation) {
                var tmp = stack.pop();
                stack.push(operation(stack.pop(), tmp));
            }
            switch (token) {
                case "+":
                    binary(add);
                    break;
                case "*":
                    binary(multiply);
                    break;
                case "/":
                    binary(divide);
                    break;
                case "-":
                    binary(subtract);
                    break;
                case "negate":
                    unary(negate);
                    break;
                case "abs":
                    unary(abs);
                    break;
                case "log":
                    unary(log);
                    break;
                case "%":
                    binary(mod);
                    break;
                case "**":
                    binary(power);
                    break;
            }
        }
    });
    return stack[0];
}