(ns bsless.eta)

(defmacro $
  "Eta expand a function to get hot reloading without var punning.
  Due to how Clojure's compiler works, eta expanded functions passed as
  values will retain their dynamism when their value is redefined in the
  REPL.
  ($ f) is functionally equivalent to (fn [& args] (apply f args))."
  [sym]
  (assert (symbol? sym))
  `(fn
     ([] (~sym))
     ([~'a] (~sym ~'a))
     ([~'a ~'b] (~sym ~'a ~'b))
     ([~'a ~'b ~'c] (~sym ~'a ~'b ~'c))
     ([~'a ~'b ~'c ~'d] (~sym ~'a ~'b ~'c ~'d))
     ([~'a ~'b ~'c ~'d ~'e] (~sym ~'a ~'b ~'c ~'d ~'e))
     ([~'a ~'b ~'c ~'d ~'e ~'& ~'args] (apply ~sym ~'a ~'b ~'c ~'d ~'e ~'args))))


(defmacro η [sym] `($ ~sym))
(defmacro h [sym] `($ ~sym))
