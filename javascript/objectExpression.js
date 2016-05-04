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
function expDiff(expr, varName) {
    return new Multiply(new Exp(expr[0]), expr[0].diff(varName));
}
function ArcTanDiff(expr, varName) {
    return new Divide(expr[0].diff(varName), new Add(new Const(1), new Multiply(expr[0], expr[0])));
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
Const.prototype.prefix = Const.prototype.toString;

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
Variable.prototype.prefix = Variable.prototype.toString;

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

    this.prefix = function () {
        return '(' + name + expressions.reduce(function (ans, expr) {
                return ans + ' ' + expr.prefix();
            }, "") + ')';
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
var Exp = createOperation(Math.exp, 'exp', expDiff);
var ArcTan = createOperation(Math.atan, 'atan', ArcTanDiff);

var operations = {
    "+": Add,
    "-": Subtract,
    "*": Multiply,
    "/": Divide,
    "negate": Negate,
    "sin": Sin,
    "cos": Cos,
    "exp": Exp,
    "atan": ArcTan
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

function ParsePrefixError(message, position) {
    this.name = "ParsePrefixError";
    this.message = message + " at " + position;
}
ParsePrefixError.prototype = Error.prototype;

function UnexpectedTokenError(token, position) {
    var message = "Unexpected Token '" + token.slice(0, 10) + (token.length > 10 ? "...<" + token.length + " characters>" : "") + "'";
    ParsePrefixError.call(this, message, position);
}
UnexpectedTokenError.prototype = ParsePrefixError.prototype;

function parsePrefix(text) {
    var tokens = text.split('').reduce(function (arr, symbol) {
        if (symbol === ')' || symbol === '(') {
            arr.push(symbol);
            arr.push('');
        } else if (symbol === ' ') {
            arr.push('');
        } else {
            arr[arr.length - 1] += symbol;
        }
        return arr;
    }, ['']).filter(function (token) {
        return token !== '';
    });
    var pos = 0;

    function isNumber(number) {
        var begin = 0;
        if (number[0] === '-') {
            begin = 1;
        }
        return number.slice(begin).split('').every(function (digit) {
            return digit >= '0' && digit <= '9';
        });
    }

    var expr = function parse(expectBracket) {
        var ans;
        if (pos >= tokens.length) {
            throw new ParsePrefixError("Unexpected end of input", pos);
        } else if (tokens[pos] === '(') {
            pos++;
            ans = parse(true);
        } else if (tokens[pos] in Variable.variables) {
            ans = new Variable(tokens[pos++]);
        } else if (tokens[pos] in operations) {
            var operation = operations[tokens[pos++]];
            var args = [];
            for (var i = 0; i < operation.size; i++) {
                args.push(parse(false));
            }
            var expr = Object.create(operation.prototype);
            operation.apply(expr, args);
            ans = expr;
        } else if (isNumber(tokens[pos])) {
            ans = new Const(parseInt(tokens[pos++]));
        } else {
            throw new UnexpectedTokenError(tokens[pos], pos);
        }
        if (expectBracket) {
            if (tokens[pos] === ')') {
                pos++;
            } else {
                throw new ParsePrefixError("Expected ')'", pos);
            }
        }
        return ans;
    }(0, false);

    if (pos < tokens.length) {
        throw new UnexpectedTokenError(tokens[pos], pos);
    } else {
        return expr;
    }
}