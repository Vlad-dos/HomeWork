(defn constant [value]
	(fn [var]
		value))

(defn variable [name]
	(fn [variables]
		(variables name)))

(defn operation [func]
	(fn [& args]
		(fn [var]
			(apply func (for [i args]
										(i var))))))

(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(def divide (operation (fn [a b] (/ (double a) (double b)))))
(def negate (operation -))

(def operations {'+ add '- subtract '* multiply '/ divide 'negate negate})

(defn parseList [expr] (cond
												 (seq? expr) (apply (operations (first expr)) (map parseList (rest expr)))
												 (symbol? expr) (variable (name expr))
												 true (constant expr)
												 ))

(defn parseFunction [strng] (parseList (read-string strng)))

(def expr (read-string "(+ x (- 1 2))"))
(println (type expr))