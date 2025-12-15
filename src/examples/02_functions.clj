(ns examples.02-functions
  (:gen-class))

;; ============================================
;; LEVEL 1: FUNCTIONS - Function Definitions
;; ============================================

;; 1. DEFINING FUNCTIONS WITH DEFN
(defn greet
  "A simple greeting function"
  [name]
  (str "Hello, " name "!"))

;; 2. MULTI-ARITY FUNCTIONS
(defn greet-multiple
  "Greet with optional greeting"
  ([name] (greet-multiple "Hello" name))
  ([greeting name] (str greeting ", " name "!")))

;; 3. VARIADIC FUNCTIONS
(defn sum
  "Sum any number of arguments"
  [& numbers]
  (apply + numbers))

(comment 
  (sum 1 3 3 4 5)
)

;; 4. ANONYMOUS FUNCTIONS (fn)
(defn example-anonymous []
  (let [add-one (fn [x] (+ x 1))
        multiply (fn [x y] (* x y))]
    (println "Add one to 5:" (add-one 5))
    (println "Multiply 3 and 4:" (multiply 3 4))))

;; 5. SHORTHAND ANONYMOUS FUNCTIONS (#)
(defn example-shorthand []
  (let [numbers [1 2 3 4 5]]
    ;; #(+ % 1) is equivalent to (fn [x] (+ x 1))
    (println "Increment each:" (map #(+ % 1) numbers))
    ;; #(+ %1 %2) is equivalent to (fn [x y] (+ x y))
    (println "Add pairs:" (map #(+ %1 %2) [1 2 3] [4 5 6]))))

;; 6. HIGHER-ORDER FUNCTIONS - Functions that take or return functions
(defn apply-twice
  "Apply a function twice to a value"
  [f x]
  (f (f x)))

(defn example-higher-order []
  (println "Double twice:" (apply-twice #(* % 2) 5))
  (println "Increment twice:" (apply-twice inc 10)))

;; 7. PARTIAL APPLICATION
(defn example-partial []
  (let [add-ten (partial + 10)
        multiply-by-three (partial * 3)]
    (println "Add 10 to 5:" (add-ten 5))
    (println "Multiply 7 by 3:" (multiply-by-three 7))))

;; 8. COMPOSITION
(defn example-composition []
  (let [add-one #(+ % 1)
        multiply-by-two #(* % 2)
        add-then-multiply (comp multiply-by-two add-one)]
    (println "Add 1 then multiply by 2:" (add-then-multiply 5))
    ;; Using comp directly
    (println "Same with comp:" ((comp #(* % 2) #(+ % 1)) 5))))

;; 9. RECURSION - Basic recursion
(defn factorial
  "Calculate factorial recursively"
  [n]
  (if (<= n 1)
    1
    (* n (factorial (dec n)))))

;; 10. TAIL RECURSION WITH RECUR
(defn factorial-tail
  "Calculate factorial with tail recursion"
  [n]
  (loop [acc 1
         num n]
    (if (<= num 1)
      acc
      (recur (* acc num) (dec num)))))

;; 11. LOOP AND RECUR
(defn example-loop []
  (loop [i 0
         result []]
    (if (>= i 5)
      result
      (recur (inc i) (conj result (* i 2))))))

;; 12. MULTIPLE RETURN VALUES
(defn divide-with-remainder
  "Return quotient and remainder"
  [dividend divisor]
  [(quot dividend divisor) (mod dividend divisor)])

(defn example-multiple-returns []
  (let [[quotient remainder] (divide-with-remainder 17 5)]
    (println "17 / 5 = " quotient " remainder " remainder)))

;; 13. FUNCTION DOCUMENTATION
(defn documented-function
  "This is a function with documentation.
  
  It takes two parameters:
  - x: a number
  - y: another number
  
  Returns the sum of x and y."
  [x y]
  (+ x y))

;; 14. PREDICATE FUNCTIONS
(defn is-even? [n] (even? n))
(defn is-positive? [n] (> n 0))

(defn example-predicates []
  (println "Is 4 even?" (is-even? 4))
  (println "Is 5 even?" (is-even? 5))
  (println "Is 10 positive?" (is-positive? 10))
  (println "Is -5 positive?" (is-positive? -5)))

;; 15. CLOSURES
(defn make-counter
  "Create a counter function"
  [initial-value]
  (let [count (atom initial-value)]
    (fn []
      (swap! count inc))))

(defn example-closures []
  (let [counter1 (make-counter 0)
        counter2 (make-counter 100)]
    (println "Counter 1:" (counter1) (counter1) (counter1))
    (println "Counter 2:" (counter2) (counter2))))

;; 16. APPLY
(defn example-apply []
  (let [numbers [1 2 3 4 5]]
    ;; apply unpacks a collection as arguments
    (println "Sum with apply:" (apply + numbers))
    (println "Max with apply:" (apply max numbers))))

;; Run examples
(defn -main []
  (println "=== 02. FUNCTIONS EXAMPLES ===\n")
  (println "Greet:" (greet "Alice"))
  (println "Greet multiple:" (greet-multiple "Bob"))
  (println "Greet with custom:" (greet-multiple "Hi" "Charlie"))
  (println "Sum:" (sum 1 2 3 4 5))
  (println)
  (example-anonymous)
  (println)
  (example-shorthand)
  (println)
  (example-higher-order)
  (println)
  (example-partial)
  (println)
  (example-composition)
  (println)
  (println "Factorial (recursive):" (factorial 5))
  (println "Factorial (tail recursive):" (factorial-tail 5))
  (println "Loop example:" (example-loop))
  (println)
  (example-multiple-returns)
  (println)
  (example-predicates)
  (println)
  (example-closures)
  (println)
  (example-apply))

