# io.github.bsless/eta

To pass by value or by reference?

On one hand, to get a "proper" dynamic development experience, we need
to pass functions by reference:

```clojure
(jetty/run-server #'app opts)
```

On the other hand, using vars instead of functions has a performance
impact, both because we pay for an extra deref, and derefing a var
introduces a volatile read, which we might not want on our hot path.

We want to just pass `app` by value:

```clojure
(jetty/run-server app opts)
```

So what do we do? Abuse the compiler.

When we refer to functions in other functions, the compiler by default will pass them by reference, and the compiled function object will hold a reference to the Var, not the function object.

If we turn `*direct-linking*` on, function will be dereferenced at compile time, not run time.

So what happens if we eta-expand a function:

```clojure
(defn foo [a] (inc a))
(def xf (map foo)) ;; pass foo by value
(def xf2 (map (fn [a] (foo a)))) ;; foo is passed by reference

(transduce xf conj [] (range 3)) ;; => [1 2 3]
(transduce xf2 conj [] (range 3)) ;; => [1 2 3]

;; Now redefine `foo` and use the transducers again
(defn foo [a] (+ a a))
(transduce xf conj [] (range 3)) ;; => [1 2 3]
(transduce xf2 conj [] (range 3)) ;; => [0 2 4] << SUCCESS
```

And the cool part? If we use direct linking in production it will just get in-lined.

This library introduces a single macro, `$`, which eta expands to
multiple arities, allowing us to enjoy both worlds in development time
and production artifacts.

## Usage

```clojure
(require '[bsless.eta :refer [$]])
(jetty/run-server ($ app) opts)
```

## License

Copyright © 2023 Bsless

Distributed under the Eclipse Public License version 1.0.
