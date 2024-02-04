(ns bsless.eta-test
  (:require
   [clojure.test :refer [is deftest testing]]
   [bsless.eta :refer [$]]))

(deftest $-test
  (is (= 1 (($ +) 1)))
  (is (= 3 (($ +) 1 2)))
  (is (= 6 (($ +) 1 2 3)))
  (is (= 55 (apply ($ +) (range 1 11)))))

(defn foo [a] (inc a))
(def xf (map foo))
(def xf2 (map ($ foo)))

(deftest value-vs-ref-test
  (testing "Establish baseline, xf and xf2 behave the same"
    (is (= [1 2 3] (transduce xf conj [] (range 3))))
    (is (= [1 2 3] (transduce xf2 conj [] (range 3)))))
  (testing "xf2 ys more dynamic than xf"
    (with-redefs [foo (fn [a] (+ a a))]
      (is (= [1 2 3] (transduce xf conj [] (range 3))))
      (is (= [0 2 4] (transduce xf2 conj [] (range 3)))))))
