(ns examples.05-higher-order
  (:gen-class))

;; ============================================
;; LEVEL 2: HIGHER-ORDER FUNCTIONS
;; ============================================

;; 1. MAP - Transform each element
(defn example-map []
  (let [numbers [1 2 3 4 5]]
    (println "Square:" (map #(* % %) numbers))
    (println "Increment:" (map inc numbers))
    (println "Multiple collections:" (map + [1 2 3] [4 5 6] [7 8 9]))
    (println "Map with index:" (map-indexed #(vector %1 %2) numbers))))

;; 2. FILTER - Select elements
(defn example-filter []
  (let [numbers (range 1 11)]
    (println "Even:" (filter even? numbers))
    (println "Greater than 5:" (filter #(> % 5) numbers))
    (println "Prime check:" (filter #(not-any? (fn [d] (zero? (mod % d)))
                                                (range 2 %))
                                    (range 2 20)))))

;; 3. REDUCE - Accumulate values
(defn example-reduce []
  (let [numbers [1 2 3 4 5]]
    (println "Sum:" (reduce + numbers))
    (println "Product:" (reduce * 1 numbers))
    (println "Max:" (reduce max numbers))
    (println "Reverse:" (reduce conj '() numbers))
    (println "Count:" (reduce (fn [acc _] (inc acc)) 0 numbers))))

;; 4. REDUCE-KV - Reduce over key-value pairs
(defn example-reduce-kv []
  (let [m {:a 1 :b 2 :c 3}]
    (println "Sum values:" (reduce-kv (fn [acc _k v] (+ acc v)) 0 m))
    (println "Keys as vector:" (reduce-kv (fn [acc k _v] (conj acc k)) [] m))))

;; 5. KEEP - Map and filter in one
(defn example-keep []
  (let [numbers [1 2 3 4 5 6]]
    (println "Keep even:" (keep #(when (even? %) (* % 2)) numbers))
    (println "Keep first:" (keep first [[1 2] [] [3 4] nil]))))

;; 6. KEEP-INDEXED - Keep with index
(defn example-keep-indexed []
  (let [items [:a :b :c :d]]
    (println "Keep even indices:" 
             (keep-indexed #(when (even? %1) %2) items))))

;; 7. MAPCAT - Map and concatenate
(defn example-mapcat []
  (let [ranges [[1 2 3] [4 5] [6 7 8 9]]]
    (println "Mapcat:" (mapcat identity ranges))
    (println "Mapcat with function:" 
             (mapcat #(repeat 2 %) [1 2 3]))))

;; 8. SOME - Find first truthy value
(defn example-some []
  (let [numbers [1 3 5 7 9]]
    (println "Has even?" (some even? numbers))
    (println "Has greater than 10?" (some #(> % 10) numbers))
    (println "First even:" (some #(when (even? %) %) [1 2 3 4]))))

;; 9. EVERY? - Check all satisfy predicate
(defn example-every? []
  (let [numbers [2 4 6 8]]
    (println "All even?" (every? even? numbers))
    (println "All positive?" (every? pos? numbers))
    (println "All greater than 10?" (every? #(> % 10) numbers))))

;; 10. NOT-ANY? - Check none satisfy predicate
(defn example-not-any? []
  (let [numbers [1 3 5 7]]
    (println "No evens?" (not-any? even? numbers))
    (println "No negatives?" (not-any? neg? numbers))))

;; 11. COMP - Function composition
(defn example-comp []
  (let [add-one #(+ % 1)
        multiply-two #(* % 2)
        square #(* % %)
        add-then-multiply (comp multiply-two add-one)
        complex (comp square multiply-two add-one)]
    (println "Add then multiply:" (add-then-multiply 5))
    (println "Complex:" (complex 5))))

;; 12. PARTIAL - Partial application
(defn example-partial []
  (let [add-ten (partial + 10)
        multiply-by-three (partial * 3)
        greet (partial str "Hello, ")]
    (println "Add 10:" (add-ten 5))
    (println "Multiply by 3:" (multiply-by-three 7))
    (println "Greet:" (greet "Alice"))))

;; 13. COMPLEMENT - Negate predicate
(defn example-complement []
  (let [not-even? (complement even?)
        not-empty? (complement empty?)]
    (println "Not even:" (filter not-even? [1 2 3 4 5]))
    (println "Not empty:" (filter not-empty? [[1 2] [] [3] []]))))

;; 14. JUXT - Apply multiple functions
(defn example-juxt []
  (let [numbers [1 2 3 4 5]
        stats ((juxt count (partial reduce +) (partial reduce max)) numbers)]
    (println "Stats (count, sum, max):" stats)
    (println "Min and max:" ((juxt min max) numbers))))

;; 15. APPLY - Unpack collection as arguments
(defn example-apply []
  (let [numbers [1 2 3 4 5]]
    (println "Sum:" (apply + numbers))
    (println "Max:" (apply max numbers))
    (println "Concat strings:" (apply str ["Hello" " " "World"]))))

;; 16. CONSTANTLY - Return constant function
(defn example-constantly []
  (let [always-five (constantly 5)]
    (println "Always five:" (map always-five [1 2 3 4]))))

;; 17. IDENTITY - Return value unchanged
(defn example-identity []
  (let [items [1 nil 2 nil 3]]
    (println "Filter truthy:" (filter identity items))
    (println "Map identity:" (map identity [1 2 3]))))

;; 18. COMPARATOR - Create comparison function
(defn example-comparator []
  (let [people [{:name "Alice" :age 30}
                 {:name "Bob" :age 25}
                 {:name "Charlie" :age 35}]
        age-comparator (comparator #(< (:age %1) (:age %2)))]
    (println "Sorted by age:" (sort age-comparator people))))

;; 19. GROUP-BY - Group by function result
(defn example-group-by []
  (let [words ["apple" "banana" "apricot" "blueberry" "avocado"]]
    (println "Group by first letter:" (group-by first words))
    (println "Group by length:" (group-by count words))))

;; 20. PARTITION-BY - Partition by function result
(defn example-partition-by []
  (let [numbers [1 1 2 2 2 3 1 1 4 4]]
    (println "Partition by value:" (partition-by identity numbers))
    (println "Partition by even:" (partition-by even? [1 2 3 4 5 6]))))

;; 21. TRANSDUCERS - Efficient transformations
(defn example-transducers []
  (let [numbers (range 100)
        xf (comp (filter even?)
                 (map #(* % %))
                 (take 5))]
    (println "Transduce:" (transduce xf conj [] numbers))
    (println "Into:" (into [] xf numbers))))

;; Run examples
(defn -main []
  (println "=== 05. HIGHER-ORDER FUNCTIONS EXAMPLES ===\n")
  (example-map)
  (println)
  (example-filter)
  (println)
  (example-reduce)
  (println)
  (example-reduce-kv)
  (println)
  (example-keep)
  (println)
  (example-keep-indexed)
  (println)
  (example-mapcat)
  (println)
  (example-some)
  (println)
  (example-every?)
  (println)
  (example-not-any?)
  (println)
  (example-comp)
  (println)
  (example-partial)
  (println)
  (example-complement)
  (println)
  (example-juxt)
  (println)
  (example-apply)
  (println)
  (example-constantly)
  (println)
  (example-identity)
  (println)
  (example-comparator)
  (println)
  (example-group-by)
  (println)
  (example-partition-by)
  (println)
  (example-transducers))

