(ns examples.04-collections
  (:require [clojure.string :as str]
            [clojure.set :as set])
  (:gen-class))

;; ============================================
;; LEVEL 2: COLLECTIONS - Lists, Vectors, Maps, Sets
;; ============================================

;; 1. VECTORS - Adding elements
(defn example-vector-operations []
  (let [v [1 2 3]]
    (println "Original:" v)
    (println "Conj (add to end):" (conj v 4))
    (println "Conj multiple:" (conj v 4 5 6))
    (println "Assoc (set index):" (assoc v 1 99))
    (println "Update:" (update v 1 inc))))

;; 2. LISTS - Adding elements (to front)
(defn example-list-operations []
  (let [lst '(1 2 3)]
    (println "Original:" lst)
    (println "Conj (add to front):" (conj lst 0))
    (println "Cons:" (cons 0 lst))
    (println "First:" (first lst))
    (println "Rest:" (rest lst))
    (println "Next:" (next lst))))

;; 3. MAPS - Operations
(defn example-map-operations []
  (let [m {:name "Alice" :age 30}]
    (println "Original:" m)
    (println "Assoc (add/update):" (assoc m :city "NYC" :age 31))
    (println "Dissoc (remove):" (dissoc m :age))
    (println "Update:" (update m :age inc))
    (println "Update-in:" (update-in {:user {:profile {:age 30}}} 
                                     [:user :profile :age] inc))
    (println "Merge:" (merge m {:city "NYC" :country "USA"}))
    (println "Merge-with:" (merge-with + {:a 1 :b 2} {:a 3 :b 4}))))

;; 4. SETS - Operations
(defn example-set-operations []
  (let [s #{1 2 3}]
    (println "Original:" s)
    (println "Conj:" (conj s 4))
    (println "Disj:" (disj s 2))
    (println "Contains?" (contains? s 2))
    (println "Union:" (set/union #{1 2} #{2 3}))
    (println "Intersection:" (set/intersection #{1 2 3} #{2 3 4}))
    (println "Difference:" (set/difference #{1 2 3} #{2 3}))))

;; 5. SEQUENCE OPERATIONS - Common to all collections
(defn example-sequence-ops []
  (let [coll [1 2 3 4 5]]
    (println "Count:" (count coll))
    (println "Empty?" (empty? coll))
    (println "Not empty?" (not-empty coll))
    (println "First:" (first coll))
    (println "Second:" (second coll))
    (println "Last:" (last coll))
    (println "Rest:" (rest coll))
    (println "Butlast:" (butlast coll))
    (println "Take:" (take 3 coll))
    (println "Drop:" (drop 2 coll))
    (println "Take-while:" (take-while #(< % 4) coll))
    (println "Drop-while:" (drop-while #(< % 3) coll))))

;; 6. FILTERING
(defn example-filtering []
  (let [numbers [1 2 3 4 5 6 7 8 9 10]]
    (println "Even numbers:" (filter even? numbers))
    (println "Odd numbers:" (filter odd? numbers))
    (println "Greater than 5:" (filter #(> % 5) numbers))
    (println "Remove nil:" (filter some? [1 nil 2 nil 3]))
    (println "Remove false:" (filter identity [true false true false]))))

;; 7. MAPPING
(defn example-mapping []
  (let [numbers [1 2 3 4 5]]
    (println "Square:" (map #(* % %) numbers))
    (println "Increment:" (map inc numbers))
    (println "Multiple collections:" (map + [1 2 3] [4 5 6]))
    (println "Map with index:" (map-indexed vector numbers))))

;; 8. REDUCING
(defn example-reducing []
  (let [numbers [1 2 3 4 5]]
    (println "Sum:" (reduce + numbers))
    (println "Product:" (reduce * numbers))
    (println "Max:" (reduce max numbers))
    (println "With initial:" (reduce + 100 numbers))
    (println "Count:" (reduce (fn [acc _] (inc acc)) 0 numbers))))

;; 9. GROUPING
(defn example-grouping []
  (let [words ["apple" "banana" "apricot" "blueberry" "avocado"]]
    (println "Group by first letter:" 
             (group-by first words))
    (println "Group by length:"
             (group-by count words))))

;; 10. PARTITIONING
(defn example-partitioning []
  (let [numbers (range 10)]
    (println "Partition 3:" (partition 3 numbers))
    (println "Partition 3 step 1:" (partition 3 1 numbers))
    (println "Partition all:" (partition-all 3 numbers))
    (println "Partition by:" (partition-by even? [1 2 3 4 5 6]))))

;; 11. SORTING
(defn example-sorting []
  (let [numbers [3 1 4 1 5 9 2 6]]
    (println "Sort:" (sort numbers))
    (println "Sort descending:" (sort > numbers))
    (println "Sort by key:" (sort-by :age [{:name "Alice" :age 30}
                                           {:name "Bob" :age 25}
                                           {:name "Charlie" :age 35}]))))

;; 12. INTERLEAVING AND INTERPOSING
(defn example-interleaving []
  (println "Interleave:" (interleave [1 2 3] [:a :b :c]))
  (println "Interpose:" (interpose ", " ["a" "b" "c"]))
  (println "Join:" (str/join ", " ["a" "b" "c"])))

;; 13. FLATTENING
(defn example-flattening []
  (let [nested [[1 2] [3 4] [5 6]]]
    (println "Flatten:" (flatten nested))
    (println "Mapcat:" (mapcat identity nested))))

;; 14. ZIPMAP AND MAP-VECTOR
(defn example-zipping []
  (println "Zipmap:" (zipmap [:a :b :c] [1 2 3]))
  (println "Map vector:" (map vector [1 2 3] [:a :b :c])))

;; 15. COLLECTIONS AS FUNCTIONS
(defn example-collections-as-functions []
  (let [v [10 20 30]
        m {:a 1 :b 2}
        s #{:x :y :z}]
    (println "Vector as function:" (v 1))
    (println "Map as function:" (m :a))
    (println "Set as function:" (s :x))
    (println "Set not found:" (s :w))))

;; 16. INTO (convert between collection types)
(defn example-into []
  (println "List to vector:" (into [] '(1 2 3)))
  (println "Vector to set:" (into #{} [1 2 2 3 3]))
  (println "Seq to map:" (into {} [[:a 1] [:b 2]]))
  (println "Conj into:" (into [1 2] [3 4 5])))

;; 17. FREQUENCIES
(defn example-frequencies []
  (let [items [1 2 2 3 3 3 4 4 4 4]]
    (println "Frequencies:" (frequencies items))
    (println "Most common:" (apply max-key val (frequencies items)))))

;; 18. DISTINCT
(defn example-distinct []
  (let [numbers [1 2 2 3 3 3 4 4 4 4]]
    (println "Distinct:" (distinct numbers))
    (println "Count distinct:" (count (distinct numbers)))))

;; Run examples
(defn -main []
  (println "=== 04. COLLECTIONS EXAMPLES ===\n")
  (example-vector-operations)
  (println)
  (example-list-operations)
  (println)
  (example-map-operations)
  (println)
  (example-set-operations)
  (println)
  (example-sequence-ops)
  (println)
  (example-filtering)
  (println)
  (example-mapping)
  (println)
  (example-reducing)
  (println)
  (example-grouping)
  (println)
  (example-partitioning)
  (println)
  (example-sorting)
  (println)
  (example-interleaving)
  (println)
  (example-flattening)
  (println)
  (example-zipping)
  (println)
  (example-collections-as-functions)
  (println)
  (example-into)
  (println)
  (example-frequencies)
  (println)
  (example-distinct))

