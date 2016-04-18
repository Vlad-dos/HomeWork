"use strict";

function cnst(value) {
    return function (x) {
        return value;
    };
}

function variable(name) {
    return function (x) {
        return x;
    };
}

function binaryOperation(operation) {
    return function (a, b) {
        return function (x) {
            return operation(a(x), b(x));
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
        if (token[0] >= '0' && token[0] <= '9') {
            stack.push(cnst(parseInt(token)));
        } else if (token === 'x') {
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