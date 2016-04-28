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

function Operation() {
    var expressions = [];
    for (var i = 0; i < arguments.length; i++) {
        expressions.push(arguments[i]);
    }

    this._operation = function () {
    };
    this._name = "";

    this.evaluate = function () {
        var argsApply = [];
        for (var i = 0; i < expressions.length; i++) {
            argsApply.push(expressions[i].evaluate.apply(expressions[i], arguments));
        }
        return this._operation.apply(null, argsApply);
    };

    this.toString = function () {
        var ans = "";
        expressions.forEach(function (expr) {
            ans += expr.toString() + " ";
        });
        return ans + this._name;
    };

    this._localSimplify = function () {
        return this;
    }

    this.simplify = function () {
        var allConst = true;
        for (var i = 0; i < expressions.length; i++) {
            expressions[i] = expressions[i].simplify();
            if (!(expressions[i] instanceof Const)) {
                allConst = false;
            }
        }
        if (allConst) {
            return new Const(this.evaluate());
        } else {
            return this._localSimplify();
        }
    };
}

Add.prototype = Object.create(Operation.prototype);
function Add(firstExpr, secondExpr) {
    Operation.call(this, firstExpr, secondExpr);

    this._name = "+";
    this._operation = function (a, b) {
        return a + b;
    };

    this.diff = function (varName) {
        return new Add(firstExpr.diff(varName), secondExpr.diff(varName));
    };

    this._localSimplify = function () {
        if (firstExpr instanceof Const && firstExpr.evaluate() === 0) {
            return secondExpr;
        } else if (secondExpr instanceof Const && secondExpr.evaluate() === 0) {
            return firstExpr;
        } else {
            return this;
        }
    };
}

Subtract.prototype = Object.create(Operation.prototype);
function Subtract(firstExpr, secondExpr) {
    Operation.call(this, firstExpr, secondExpr);

    this._name = "-";
    this._operation = function (a, b) {
        return a - b;
    };

    this.diff = function (varName) {
        return new Subtract(firstExpr.diff(varName), secondExpr.diff(varName));
    };

    this._localSimplify = function () {
        if (secondExpr instanceof Const && secondExpr.evaluate() === 0) {
            return firstExpr;
        } else {
            return this;
        }
    };
}

Multiply.prototype = Object.create(Operation.prototype);
function Multiply(firstExpr, secondExpr) {
    Operation.call(this, firstExpr, secondExpr);

    this._name = "*";
    this._operation = function (a, b) {
        return a * b;
    };

    this.diff = function (varName) {
        return new Add(new Multiply(firstExpr.diff(varName), secondExpr), new Multiply(secondExpr.diff(varName), firstExpr));
    };

    this._localSimplify = function () {
        if (firstExpr instanceof Const && firstExpr.evaluate() === 1) {
            return secondExpr;
        } else if (secondExpr instanceof Const && secondExpr.evaluate() === 1) {
            return firstExpr;
        } else if ((firstExpr instanceof Const && firstExpr.evaluate() === 0) || (secondExpr instanceof Const && secondExpr.evaluate() === 0)) {
            return new Const(0);
        } else {
            return this;
        }
    };
}

Divide.prototype = Object.create(Operation.prototype);
function Divide(firstExpr, secondExpr) {
    Operation.call(this, firstExpr, secondExpr);

    this._name = "/";
    this._operation = function (a, b) {
        return a / b;
    };

    this.diff = function (varName) {
        return new Divide(new Add(new Multiply(firstExpr.diff(varName), secondExpr), new Multiply(secondExpr.diff(varName), firstExpr)), new Multiply(secondExpr, secondExpr));
    };

    this._localSimplify = function () {
        if (secondExpr instanceof Const && secondExpr.evaluate() === 1) {
            return firstExpr;
        } else if ((firstExpr instanceof Const && firstExpr.evaluate() === 0) && (secondExpr instanceof Const && secondExpr.evaluate() !== 0)) {
            return new Const(0);
        } else {
            return this;
        }
    };
}

