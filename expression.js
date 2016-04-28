"use strict";

function cnst(value) {
    return function() {
        return value;
    };
}

var variables = {"x" : 0, "y" : 1, "z" : 2};

function variable(name) {
    return function () {
        return arguments[variables[name]];
    };
}

function operation(func) {
    return function () {
        var args = arguments;
        return function () {
            var argsApply = [];
            for (var i = 0; i < args.length; i++) {
                argsApply.push(args[i].apply(null, arguments));
            }
            return func.apply(null, argsApply);
        };
    };
}

/*
var add = operation((a, b) => a + b);
var multiply = operation((a, b) => a * b);
var subtract = operation((a, b) => a - b);
var divide = operation((a, b) => a / b);
var mod = operation((a, b) => a % b);
var power = operation((a, b) => Math.pow(a, b));

var negate = operation((a) => -a);
var log = operation((a) => Math.log(a));
var abs = operation((a) => Math.abs(a));

var med = operation((a, b, c) => [a, b, c].sort()[1]);
*/

var add = operation(function (a, b) {
    return a + b;
});
var multiply = operation(function (a, b) {
    return a * b;
});
var subtract = operation(function (a, b) {
    return a - b;
});
var divide = operation(function (a, b) {
    return a / b;
});
var mod = operation(function (a, b) {
    return a % b;
});
var power = operation(Math.pow);

var negate = operation(function (a) {
    return -a;
});
var abs = operation(Math.abs);
var log = operation(Math.log);

var med = function(pos) {
    return operation(function () {
        var args = [];
        for (var i = 0; i < arguments.length; i++) {
            args.push(arguments[i]);
        }
        return args.sort()[pos];
    });
}

var med3 = med(1);
var med15 = med(7);

var unary = {
    "negate" : negate,
    "abs" : abs,
    "log" : log
};

var binary = {
    "+" : add,
    "-" : subtract,
    "*" : multiply,
    "/" : divide,
    "%" : mod,
    "**" : power
};

var randomnary = {
    "med" : med3,
    "med15" : med15
}

var randomnarysize = {
    "med" : 3,
    "med15" : 15
}

function parse(text) {
    var stack = [];
    text.trim().split(" ").forEach(function (token) {
        if (token in variables) {
            stack.push(variable(token));
        } else if (token in unary) {
            stack.push(unary[token](stack.pop()));
        } else if (token in binary) {
            var tmp = stack.pop();
            stack.push(binary[token](stack.pop(), tmp));
        } else if (token in randomnary) {
            var args = [];
            for (var i = 0; i < randomnarysize[token]; i++) {
                args.push(stack.pop());
            }
            args.reverse();
            stack.push(randomnary[token](args));
        } else if (token) {
            stack.push(cnst(parseInt(token)));
        }
    });
    return stack[0];
}
