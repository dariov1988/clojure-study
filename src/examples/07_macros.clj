(ns examples.07-macros
  (:require [clojure.string :as str])
  (:gen-class))

;; ============================================
;; LEVEL 3: MACROS - Metaprogramming
;; ============================================

;; 1. UNDERSTANDING MACROS - Code that generates code
;; Macros receive code as data and return code

;; 2. DEFMACRO - Define a macro
(defmacro unless
  "Execute body if condition is false"
  [condition & body]
  `(if (not ~condition)
     (do ~@body)))

(defn example-unless []
  (unless false
    (println "This will print")
    (println "This too")))

;; 3. SYNTAX QUOTE (`) - Creates code template
(defmacro my-when
  "Execute body if condition is true"
  [condition & body]
  `(if ~condition
     (do ~@body)))

(defn example-my-when []
  (my-when true
    (println "Condition is true")
    (println "Multiple statements")))

;; 4. UNQUOTE (~) - Evaluate expression in syntax quote
(defmacro debug
  "Print value and return it"
  [expr]
  `(let [result# ~expr]
     (println "Debug:" '~expr "=" result#)
     result#))

(defn example-debug []
  (let [x 10
        y 20]
    (debug (+ x y))))

;; 5. UNQUOTE-SPLICING (~@) - Insert multiple forms
(defmacro my-let
  "Simple let macro"
  [bindings & body]
  (let [vars (take-nth 2 bindings)
        vals (take-nth 2 (rest bindings))]
    `((fn [~@vars]
        ~@body)
      ~@vals)))

(defn example-my-let []
  #_{:clj-kondo/ignore [:unresolved-symbol]}
  (my-let [x 10
           y 20]
    (println "Sum:" (+ x y))))

;; 6. GENSYM (#) - Generate unique symbols
(defmacro safe-inc
  "Increment without name collision"
  [x]
  (let [result (gensym)]
    `(let [~result ~x]
       (inc ~result))))

;; Or use auto-gensym
(defmacro safe-inc-2
  "Increment with auto-gensym"
  [x]
  `(let [result# ~x]
     (inc result#)))

;; 7. MACROEXPAND - See what macro generates
(defn example-macroexpand []
  (println "Unless expansion:")
  (println (macroexpand-1 '(unless false (println "test"))))
  (println "\nFull expansion:")
  (println (macroexpand '(unless false (println "test")))))

;; 8. CONDITIONAL MACRO
(defmacro my-cond->
  "Threading macro with conditions"
  [expr & clauses]
  (reduce (fn [acc [test form]]
            `(if ~test
               (-> ~acc ~form)
               ~acc))
          expr
          (partition 2 clauses)))

;; 9. INFIX MACRO - Custom syntax
(defmacro infix
  "Evaluate infix expressions"
  [[operand1 op operand2]]
  `(~op ~operand1 ~operand2))

(defn example-infix []
  (println "Infix:" (infix [2 + 3])))

;; 10. LOOP MACRO - Custom loop syntax
(defmacro my-loop
  "Custom loop macro"
  [bindings & body]
  (let [vars (take-nth 2 bindings)
        vals (take-nth 2 (rest bindings))
        init-bindings (mapcat vector vars vals)]
    `(loop [~@init-bindings]
       ~@body)))

;; 11. WITH-OPEN MACRO - Resource management
(defmacro with-resource
  "Ensure resource is closed"
  [[name resource] & body]
  `(let [~name ~resource]
     (try
       ~@body
       (finally
         (.close ~name)))))

;; 12. DEFN MACRO - Understanding function definition
;; defn is actually a macro that expands to def + fn

;; 13. THREADING MACROS - -> and ->>
;; These are macros that rewrite code

(defn example-threading []
  (let [data {:numbers [1 2 3 4 5]}]
    (println "Thread-first:" 
             (-> data
                 :numbers
                 count
                 (* 2)))
    (println "Thread-last:"
             (->> data
                  :numbers
                  (map inc)
                  (filter even?)
                  (reduce +)))))

;; 14. COND-> MACRO - Conditional threading
(defn example-cond-> []
  (let [x 10]
    (println "Cond->:"
             (cond-> x
               true (* 2)
               (> x 5) (+ 10)
               :always inc))))

;; 15. SOME-> MACRO - Short-circuit threading
(defn example-some-> []
  (let [data {:user {:profile {:name "Alice"}}}]
    (println "Some->:"
             (some-> data
                     :user
                     :profile
                     :name
                     str/upper-case))))

;; 16. AS-> MACRO - Named intermediate values
(defn example-as-> []
  (println "As->:"
           (as-> [1 2 3] x
             (map inc x)
             (filter even? x)
             (reduce + x))))

;; 17. COMPLEX MACRO - Building DSL
(defmacro defvalidator
  "Create a validator function"
  [name & rules]
  (let [rule-map (apply hash-map rules)]
    `(defn ~name [value#]
       (and
         ~@(map (fn [[pred msg]]
                  `(or (~pred value#)
                       (do (println ~msg) false)))
                (partition 2 (apply concat (map vector (keys rule-map) 
                                                 (vals rule-map)))))))))

;; 18. MACRO HYGIENE - Avoiding variable capture
(defmacro capture-example
  "Example of potential capture"
  [x]
  `(let [result 100]
     (+ ~x result)))

;; 19. QUOTE vs SYNTAX QUOTE
(defn example-quotes []
  (println "Quote:" '(list 1 2 3))
  (println "Syntax quote:" `(list 1 2 3))
  (println "Unquote in syntax quote:" `(list 1 2 ~(+ 1 2))))

;; 20. MACRO DEBUGGING
(defmacro dbg
  "Debug macro expansion"
  [form]
  (let [expanded (macroexpand form)]
    `(do
       (println "Original:" '~form)
       (println "Expanded:" '~expanded)
       ~expanded)))

;; Run examples
(defn -main []
  (println "=== 07. MACROS EXAMPLES ===\n")
  (example-unless)
  (println)
  (example-my-when)
  (println)
  (example-debug)
  (println)
  (example-my-let)
  (println)
  (example-macroexpand)
  (println)
  (example-infix)
  (println)
  (example-threading)
  (println)
  (example-cond->)
  (println)
  (example-some->)
  (println)
  (example-as->)
  (println)
  (example-quotes))

