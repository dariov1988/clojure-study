(ns examples.03-control-flow
  (:require [clojure.string :as str])
  (:gen-class))

;; ============================================
;; LEVEL 1: CONTROL FLOW - Conditionals and Loops
;; ============================================

;; 1. IF EXPRESSION
(defn example-if []
  (let [x 10]
    (if (> x 5)
      "x is greater than 5"
      "x is not greater than 5")))

;; 2. IF-NOT
(defn example-if-not []
  (let [name nil]
    (if-not name
      "Name is not provided"
      (str "Hello, " name))))

;; 3. WHEN (if without else)
(defn example-when []
  (let [x 10]
    (when (> x 5)
      (println "x is greater than 5")
      "This is returned")))

;; 4. WHEN-NOT
(defn example-when-not []
  (let [name nil]
    (when-not name
      (println "Name is missing")
      "Default name")))

;; 5. COND (multiple conditions)
(defn grade-letter [score]
  (cond
    (>= score 90) "A"
    (>= score 80) "B"
    (>= score 70) "C"
    (>= score 60) "D"
    :else "F"))

;; 6. CASE (pattern matching on values)
(defn day-of-week [n]
  (case n
    1 "Monday"
    2 "Tuesday"
    3 "Wednesday"
    4 "Thursday"
    5 "Friday"
    6 "Saturday"
    7 "Sunday"
    "Invalid day"))

;; 7. COND-> (threading with conditions)
(defn example-cond-> []
  (let [x 10]
    (cond-> x
      (> x 5) (* 2)
      (> x 10) (+ 5)
      :always inc)))

;; 8. SOME-> (short-circuiting threading)
(defn example-some-> []
  (let [data {:user {:profile {:name "Alice"}}}]
    (some-> data
            :user
            :profile
            :name
            str/upper-case)))

;; 9. DOSEQ (side-effect loop)
(defn example-doseq []
  (println "Counting from 1 to 5:")
  (doseq [i (range 1 6)]
    (println i)))

;; 10. FOR (list comprehension)
(defn example-for []
  (println "Squares of 1-5:" (for [x (range 1 6)] (* x x)))
  (println "Pairs:" (for [x [1 2] y [3 4]] [x y]))
  (println "Filtered:" (for [x (range 1 10) :when (even? x)] x)))

;; 11. WHILE (loop with condition)
(defn example-while []
  (let [counter (atom 0)]
    (while (< @counter 5)
      (println "Counter:" @counter)
      (swap! counter inc))
    @counter))

;; 12. DO (evaluate multiple expressions)
(defn example-do []
  (println "First expression")
  (println "Second expression")
  "This is returned")

;; 13. AND (short-circuiting logical AND)
(defn example-and []
  (println "All true:" (and true true true))
  (println "One false:" (and true false true))
  (println "Short-circuit:" (and false (println "This won't print"))))

;; 14. OR (short-circuiting logical OR)
(defn example-or []
  (println "All false:" (or false false false))
  (println "One true:" (or false true false))
  (println "Short-circuit:" (or true (println "This won't print"))))

;; 15. NOT
(defn example-not []
  (println "Not true:" (not true))
  (println "Not false:" (not false))
  (println "Not nil:" (not nil)))

;; 16. SOME (find first truthy value)
(defn example-some []
  (let [numbers [1 2 3 4 5]]
    (println "Has even?" (some even? numbers))
    (println "Has greater than 10?" (some #(> % 10) numbers))
    (println "First even:" (some #(when (even? %) %) numbers))))

;; 17. EVERY? (check if all satisfy predicate)
(defn example-every? []
  (let [numbers [2 4 6 8]]
    (println "All even?" (every? even? numbers))
    (println "All positive?" (every? pos? numbers))
    (println "All greater than 10?" (every? #(> % 10) numbers))))

;; 18. IF-LET (bind and check in one)
(defn example-if-let []
  (let [data {:name "Alice"}]
    (if-let [name (:name data)]
      (str "Hello, " name)
      "Name not found")))

;; 19. WHEN-LET (bind and check, no else)
(defn example-when-let []
  (let [data {:age 30}]
    (when-let [age (:age data)]
      (println "Age is" age)
      (* age 2))))

;; 20. COND->> (thread-last with conditions)
(defn example-cond->> []
  (let [numbers [1 2 3 4 5]]
    (cond->> numbers
      true (map inc)
      true (filter even?)
      true (reduce +))))

;; Run examples
(defn -main []
  (println "=== 03. CONTROL FLOW EXAMPLES ===\n")
  (println "If example:" (example-if))
  (println "If-not example:" (example-if-not))
  (println "When example:" (example-when))
  (println "When-not example:" (example-when-not))
  (println)
  (println "Grade for 85:" (grade-letter 85))
  (println "Day 3:" (day-of-week 3))
  (println "Cond-> example:" (example-cond->))
  (println "Some-> example:" (example-some->))
  (println)
  (example-doseq)
  (println)
  (example-for)
  (println)
  (println "While example result:" (example-while))
  (println)
  (example-do)
  (println)
  (example-and)
  (example-or)
  (example-not)
  (println)
  (example-some)
  (example-every?)
  (println)
  (println "If-let example:" (example-if-let))
  (println "When-let example:" (example-when-let))
  (println "Cond->> example:" (example-cond->>)))

