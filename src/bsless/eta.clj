(ns bsless.eta)

(defmacro $ [sym]
  (assert (symbol? sym))
  `(fn
     ([] (~sym))
     ([~'a] (~sym ~'a))
     ([~'a ~'b] (~sym ~'a ~'b))
     ([~'a ~'b ~'c] (~sym ~'a ~'b ~'c))
     ([~'a ~'b ~'c ~'d] (~sym ~'a ~'b ~'c ~'d))
     ([~'a ~'b ~'c ~'d ~'e] (~sym ~'a ~'b ~'c ~'d ~'e))
     ([~'a ~'b ~'c ~'d ~'e ~'& ~'args] (apply ~sym ~'a ~'b ~'c ~'d ~'e ~'args))))
