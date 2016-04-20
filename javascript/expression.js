"use strict";

function cnst(value) {
    return function (x, y, z) {
        return value;
    };
}

function variable(name) {
    return function (x, y, z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                return 0;
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

var add = binaryOperation((a, b) => a + b);
var multiply = binaryOperation((a, b) => a * b);
var subtract = binaryOperation((a, b) => a - b);
var divide = binaryOperation((a, b) => a / b);

function parse(text) {
    var tokens = text.split(" ");
    var stack = [];
    tokens.forEach(function (token) {
        if (token[0] >= '0' && token[0] <= '9' || (token[0] === '-' && token[1] >= '0' && token[1] <= '9')) {
            stack.push(cnst(parseInt(token)));
        } else if (token >= "x" && token <= "z") {
            stack.push(variable(token));
        } else {
            switch (token) {
                case "+":
                    var tmp = stack.pop();
                    stack.push(add(stack.pop(), tmp));
                    break;
                case "*":
                    tmp = stack.pop();
                    stack.push(multiply(stack.pop(), tmp));
                    break;
                case "/":
                    tmp = stack.pop();
                    stack.push(divide(stack.pop(), tmp));
                    break;
                case "-":
                    tmp = stack.pop();
                    stack.push(subtract(stack.pop(), tmp));
                    break;
            }
        }
    });
    return stack[0];
}