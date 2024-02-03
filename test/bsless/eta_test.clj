(ns bsless.eta-test
  (:require
   [clojure.test :refer [is deftest]]
   [bsless.eta :refer [$]]))

(deftest $-test
  (is (= 1 (($ +) 1)))
  (is (= 3 (($ +) 1 2)))
  (is (= 6 (($ +) 1 2 3)))
  (is (= 55 (apply ($ +) (range 1 11)))))
