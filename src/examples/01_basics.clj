(ns examples.01-basics
  (:require [clojure.string :as str])
  (:gen-class))

;; ============================================
;; LEVEL 1: BASICS - Syntax and Data Structures
;; ============================================

;; 1. COMMENTS
;; Single-line comments start with ;;
;; This is a comment

#_ "This is a comment form that ignores the next form"

;; 2. DATA TYPES - Primitives
(def my-integer 42)
(def my-float 3.14)
(def my-string "Hello, Clojure!")
(def my-boolean true)
(def my-nil nil)

;; 3. COLLECTIONS - Lists
;; Lists are created with parentheses and are evaluated as function calls
;; To create a literal list, use quote or '
(def my-list '(1 2 3 4 5))
(def my-list-2 (list 1 2 3 4 5))

;; 4. COLLECTIONS - Vectors
;; Vectors are created with square brackets
(def my-vector [1 2 3 4 5])
(def my-vector-2 (vector 1 2 3 4 5))

;; 5. COLLECTIONS - Maps
;; Maps are key-value pairs in curly braces
(def my-map {:name "Alice" :age 30 :city "New York"})
(def my-map-2 (hash-map :name "Bob" :age 25))

;; 6. COLLECTIONS - Sets
;; Sets are created with #{} or (hash-set ...)
(def my-set #{1 2 3 4 5})
(def my-set-2 (hash-set 1 2 3))

;; 7. KEYWORDS
;; Keywords start with : and are often used as map keys
(def my-keyword :hello)
(def my-keyword-2 :world)

;; 8. ACCESSING COLLECTIONS
;; Get element from vector by index
(defn example-vector-access []
  (println "First element:" (get my-vector 0))
  (println "First element (sugar):" (my-vector 0))
  (println "With default:" (get my-vector 10 :not-found)))

;; Get value from map by key
(defn example-map-access []
  (println "Name:" (:name my-map))
  (println "Age:" (get my-map :age))
  (println "City:" (my-map :city))
  (println "With default:" (get my-map :country "Unknown")))

;; 9. IMMUTABILITY
;; Clojure collections are immutable - operations return new collections
(defn example-immutability []
  (let [original [1 2 3]
        modified (conj original 4)]
    (println "Original:" original)
    (println "Modified:" modified)
    (println "Original unchanged:" original)))

;; 10. BASIC OPERATIONS
(defn example-operations []
  (println "Addition:" (+ 1 2 3 4))
  (println "Subtraction:" (- 10 3 2))
  (println "Multiplication:" (* 2 3 4))
  (println "Division:" (/ 10 2))
  (println "Modulo:" (mod 10 3))
  (println "Comparison:" (= 5 5))
  (println "Not equal:" (not= 5 3))
  (println "Greater than:" (> 10 5))
  (println "Less than:" (< 3 5)))

;; 11. STRING OPERATIONS
(defn example-strings []
  (println "Concatenation:" (str "Hello" " " "World"))
  (println "Length:" (count "Hello"))
  (println "Substring:" (subs "Hello World" 0 5))
  (println "Upper case:" (str/upper-case "hello"))
  (println "Lower case:" (str/lower-case "HELLO")))

;; 12. TYPE CHECKING
(defn example-types []
  (println "Is string?" (string? "hello"))
  (println "Is number?" (number? 42))
  (println "Is keyword?" (keyword? :hello))
  (println "Is map?" (map? {:a 1}))
  (println "Is vector?" (vector? [1 2 3]))
  (println "Is list?" (list? '(1 2 3))))

;; 13. NAMESPACE AND DEF
;; def creates a global binding
(def global-value "I'm global")

;; let creates local bindings
(defn example-let []
  (let [local-value "I'm local"
        x 10
        y 20]
    (println "Local value:" local-value)
    (println "Sum:" (+ x y))))

;; 14. DESTRUCTURING
(defn example-destructuring []
  ;; Destructure vector
  (let [[first second third] [1 2 3]]
    (println "First:" first "Second:" second "Third:" third))
  
  ;; Destructure map
  (let [{name :name age :age} {:name "Alice" :age 30}]
    (println "Name:" name "Age:" age))
  
  ;; Destructure with :keys
  (let [{:keys [name age]} {:name "Bob" :age 25}]
    (println "Name:" name "Age:" age)))

;; 15. TRUTHINESS
;; In Clojure, only nil and false are falsy; everything else is truthy
(defn example-truthiness []
  (println "nil is falsy:" (if nil "true" "false"))
  (println "false is falsy:" (if false "true" "false"))
  (println "0 is truthy:" (if 0 "true" "false"))
  (println "empty string is truthy:" (if "" "true" "false"))
  (println "empty vector is truthy:" (if [] "true" "false")))

;; Run examples
(defn -main []
  (println "=== 01. BASICS EXAMPLES ===\n")
  (example-vector-access)
  (println)
  (example-map-access)
  (println)
  (example-immutability)
  (println)
  (example-operations)
  (println)
  (example-strings)
  (println)
  (example-types)
  (println)
  (example-let)
  (println)
  (example-destructuring)
  (println)
  (example-truthiness))

