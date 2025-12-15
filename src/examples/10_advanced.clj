(ns examples.10-advanced
  (:require [clojure.string :as str])
  (:gen-class))

;; ============================================
;; LEVEL 3: ADVANCED - Advanced Patterns
;; ============================================

;; 1. THREADING MACROS - Advanced usage
(defn example-threading []
  (let [data {:users [{:name "Alice" :age 30}
                       {:name "Bob" :age 25}]}]
    (println "Thread-first:"
             (-> data
                 :users
                 first
                 :name
                 str/upper-case))
    (println "Thread-last:"
             (->> data
                  :users
                  (map :age)
                  (filter #(> % 25))
                  (reduce +)))
    (println "As->:"
             (as-> data x
               (:users x)
               (map :age x)
               (reduce + x)
               (/ x (count (:users data)))))))

;; 2. COND-> AND COND->> - Conditional threading
(defn example-cond-threading []
  (let [x 10]
    (println "Cond->:"
             (cond-> x
               true (* 2)
               (> x 5) (+ 10)
               (< x 20) (- 5)
               :always inc))
    (println "Cond->>:"
             (cond->> (range 10)
               true (map inc)
               true (filter even?)
               true (take 5)
               true (reduce +)))))

;; 3. SOME-> AND SOME->> - Short-circuit threading
(defn example-some-threading []
  (let [data {:user {:profile {:name "Alice"}}}]
    (println "Some->:"
             (some-> data
                     :user
                     :profile
                     :name
                     str/upper-case))
    (println "Some->> (nil safe):"
             (some->> [1 2 3 nil 4 5]
                      (filter some?)
                      (map inc)
                      (reduce +)))))

;; 4. TRANSDUCERS - Efficient transformations
(defn example-transducers []
  (let [numbers (range 1000)
        xf (comp
             (filter even?)
             (map #(* % %))
             (take 10))]
    (println "Transduce:" (transduce xf + 0 numbers))
    (println "Into:" (into [] xf numbers))
    (println "Sequence:" (sequence xf numbers))))

;; 5. CUSTOM TRANSDUCERS
(defn mapcatting [f]
  (fn [rf]
    (fn
      ([] (rf))
      ([result] (rf result))
      ([result input]
       (reduce rf result (f input))))))

(defn example-custom-transducer []
  (let [xf (mapcatting #(repeat 2 %))
        numbers [1 2 3]]
    (println "Custom transducer:" (into [] xf numbers))))

;; 6. LAZY SEQUENCES - Infinite sequences
(defn example-infinite []
  (let [fibonacci (map first
                       (iterate (fn [[a b]] [b (+ a b)])
                                [0 1]))
        primes (filter (fn [n]
                         (not-any? #(zero? (mod n %))
                                   (range 2 (inc (int (Math/sqrt n))))))
                       (drop 2 (range)))]
    (println "First 10 Fibonacci:" (take 10 fibonacci))
    (println "First 10 primes:" (take 10 primes))))

;; 7. MEMOIZATION - Cache function results
;; Note: slow-fib is intentionally inefficient to demonstrate memoization
(defn slow-fib [n]
  (if (< n 2)
    n
    (+ (slow-fib (dec n))
       (slow-fib (- n 2)))))

(def fast-fib (memoize slow-fib))

(defn example-memoization []
  (println "Slow fib (will take time):")
  (time (slow-fib 25)) ; Using 25 instead of 30 to avoid very long wait
  (println "Fast fib (first call - computes):")
  (time (fast-fib 25))
  (println "Fast fib (second call - cached):")
  (time (fast-fib 25))) ; Cached

;; 8. PARTIAL APPLICATION - Advanced
(defn example-partial-advanced []
  (let [_add (partial +)
        multiply (partial * 2)
        greet (partial str "Hello, ")]
    (println "Partial add:" ((partial + 10) 5))
    (println "Partial multiply:" (multiply 7))
    (println "Partial greet:" (greet "World"))))

;; 9. FUNCTION COMPOSITION - Advanced
(defn example-composition-advanced []
  (let [compose (fn [& fns]
                  (fn [& args]
                    (reduce (fn [result f] (f result))
                            (apply (last fns) args)
                            (reverse (butlast fns)))))
        add-one #(+ % 1)
        multiply-two #(* % 2)
        square #(* % %)
        complex (compose square multiply-two add-one)]
    (println "Complex composition:" (complex 5))))

;; 10. DESTRUCTURING - Advanced patterns
(defn example-destructuring-advanced []
  ;; Nested destructuring
  (let [[a [b c]] [1 [2 3]]]
    (println "Nested:" a b c))
  
  ;; Map destructuring with defaults
  (let [{:keys [name age city] :or {city "Unknown"}} {:name "Alice" :age 30}]
    (println "With defaults:" name age city))
  
  ;; Rest in destructuring
  (let [[first & rest] [1 2 3 4 5]]
    (println "First:" first "Rest:" rest))
  
  ;; :as for whole collection
  (let [{:keys [name] :as person} {:name "Bob" :age 25}]
    (println "Name:" name "Person:" person)))

;; 11. PRE AND POST CONDITIONS
(defn example-pre-post []
  (defn divide
    [dividend divisor]
    {:pre [(not= divisor 0) (> dividend 0)]
     :post [(> % 0)]}
    (/ dividend divisor))
  
  (try
    (println "Divide:" (divide 10 2))
    (divide 10 0)
    (catch AssertionError _e
      (println "Precondition failed"))))

;; 12. VAR QUOTES AND EVAL
(defn example-var-quotes []
  (let [x 10
        var-x #'x]
    (println "Value:" @var-x)
    (println "Var name:" (str var-x))
    (println "Eval:" (eval '(+ 1 2)))))

;; 13. NAMESPACE MANIPULATION
(defn example-namespace []
  (println "Current namespace:" (str *ns*))
  (println "Namespace name:" (ns-name *ns*))
  (println "Namespace map:" (ns-map *ns*)))

;; 14. METADATA
(defn example-metadata []
  (let [data ^{:type :user :version 1} {:name "Alice"}]
    (println "Metadata:" (meta data))
    (println "Type:" (:type (meta data)))
    (let [with-meta (with-meta data {:type :admin})]
      (println "New metadata:" (meta with-meta)))))

;; 15. READER MACROS
(defn example-reader-macros []
  (println "Regex:" #"\d+")
  (println "Var quote:" #'example-reader-macros)
  (println "Anonymous function:" #(+ % 1))
  (println "Set:" #{1 2 3})
  (println "Tagged literal:" #inst "2023-01-01"))

;; 16. CONDITIONAL EVALUATION
(defn example-conditional []
  (let [x 10]
    (and (> x 5) (println "x is greater than 5"))
    (or (< x 5) (println "x is not less than 5"))
    (when (> x 5)
      (println "x is greater than 5")
      "return value")))

;; 17. ERROR HANDLING
(defn example-error-handling []
  (try
    (/ 10 0)
    (catch ArithmeticException e
      (println "Caught:" (.getMessage e))
      :error)
    (finally
      (println "Finally block"))))

;; 18. JAVA INTEROP
(defn example-java-interop []
  (let [list (java.util.ArrayList.)]
    (.add list "Hello")
    (.add list "World")
    (println "Java list:" list)
    (println "Size:" (.size list))
    (println "Static method:" (Math/sqrt 16))))

;; 19. STATE MANAGEMENT PATTERNS
(defn example-state-patterns []
  (let [state (atom {:counter 0 :items []})]
    (swap! state (fn [s]
                   (-> s
                       (update :counter inc)
                       (update :items conj "item"))))
    (println "State:" @state)))

;; 20. RECURSIVE PATTERNS
(defn example-recursive-patterns []
  (defn tree-sum
    "Sum all numbers in a nested structure"
    [tree]
    (if (coll? tree)
      (reduce + (map tree-sum tree))
      (if (number? tree) tree 0)))
  
  (println "Tree sum:" (tree-sum [1 [2 [3 4]] 5])))

;; Run examples
(defn -main []
  (println "=== 10. ADVANCED EXAMPLES ===\n")
  (example-threading)
  (println)
  (example-cond-threading)
  (println)
  (example-some-threading)
  (println)
  (example-transducers)
  (println)
  (example-custom-transducer)
  (println)
  (example-infinite)
  (println)
  (example-partial-advanced)
  (println)
  (example-composition-advanced)
  (println)
  (example-destructuring-advanced)
  (println)
  (example-pre-post)
  (println)
  (example-metadata)
  (println)
  (example-reader-macros)
  (println)
  (example-error-handling)
  (println)
  (example-java-interop)
  (println)
  (example-state-patterns)
  (println)
  (example-recursive-patterns))

