(ns examples.06-sequences
  (:gen-class))

;; ============================================
;; LEVEL 2: SEQUENCES - Lazy Sequences
;; ============================================

;; 1. RANGE - Generate sequences
(defn example-range []
  (println "Range 0-5:" (range 5))
  (println "Range 1-10:" (range 1 11))
  (println "Range with step:" (range 0 10 2))
  (println "Infinite range:" (take 10 (range))))

;; 2. REPEAT - Repeat values
(defn example-repeat []
  (println "Repeat 5:" (repeat 5 :hello))
  (println "Infinite repeat:" (take 5 (repeat :x)))
  (println "Cycle:" (take 10 (cycle [1 2 3]))))

;; 3. ITERATE - Generate sequence from function
(defn example-iterate []
  (println "Powers of 2:" (take 5 (iterate #(* % 2) 1)))
  (println "Fibonacci:" (take 10 (map first 
                                      (iterate (fn [[a b]] [b (+ a b)]) 
                                               [0 1]))))
  (println "Countdown:" (take 5 (iterate dec 10))))

;; 4. LAZY SEQUENCES - Not evaluated until needed
(defn example-lazy []
  (let [lazy-squares (map #(* % %) (range))
        first-five (take 5 lazy-squares)]
    (println "Lazy squares (first 5):" first-five)
    (println "Still lazy:" (type lazy-squares))))

;; 5. TAKE - Get first n elements
(defn example-take []
  (let [numbers (range)]
    (println "Take 5:" (take 5 numbers))
    (println "Take-while < 10:" (take-while #(< % 10) numbers))
    (println "Take-nth:" (take-nth 3 (range 20)))))

;; 6. DROP - Skip elements
(defn example-drop []
  (let [numbers (range 10)]
    (println "Drop 3:" (drop 3 numbers))
    (println "Drop-while < 5:" (drop-while #(< % 5) numbers))
    (println "Drop-last 2:" (drop-last 2 numbers))))

;; 7. SPLIT-AT - Split sequence at index
(defn example-split-at []
  (let [numbers (range 10)]
    (println "Split at 5:" (split-at 5 numbers))
    (println "Split-with even:" (split-with even? numbers))))

;; 8. PARTITION - Group elements
(defn example-partition []
  (let [numbers (range 10)]
    (println "Partition 3:" (partition 3 numbers))
    (println "Partition 3 step 1:" (partition 3 1 numbers))
    (println "Partition-all 3:" (partition-all 3 numbers))
    (println "Partition-by even:" (partition-by even? numbers))))

;; 9. INTERLEAVE - Interleave sequences
(defn example-interleave []
  (println "Interleave:" (interleave [1 2 3] [:a :b :c]))
  (println "Interpose:" (interpose ", " ["a" "b" "c"]))
  (println "Take interleaved:" (take 5 (interleave (range) (range 100 200)))))

;; 10. CONCAT - Concatenate sequences
(defn example-concat []
  (println "Concat:" (concat [1 2] [3 4] [5 6]))
  (println "Concat lazy:" (take 10 (concat (range) (range 100)))))

;; 11. DISTINCT - Remove duplicates
(defn example-distinct []
  (let [numbers [1 2 2 3 3 3 4 4 4 4]]
    (println "Distinct:" (distinct numbers))
    (println "Distinct lazy:" (take 5 (distinct (cycle [1 2 3]))))))

;; 12. DEDUPE - Remove consecutive duplicates
(defn example-dedupe []
  (let [numbers [1 1 2 2 2 3 1 1 4 4]]
    (println "Dedupe:" (dedupe numbers))))

;; 13. FREQUENCIES - Count occurrences
(defn example-frequencies []
  (let [items [1 2 2 3 3 3 4 4 4 4]]
    (println "Frequencies:" (frequencies items))))

;; 14. GROUP-BY - Group by function
(defn example-group-by []
  (let [words ["apple" "banana" "apricot" "blueberry" "avocado"]]
    (println "Group by first letter:" (group-by first words))
    (println "Group by length:" (group-by count words))))

;; 15. SORT - Sort sequences
(defn example-sort []
  (let [numbers [3 1 4 1 5 9 2 6]]
    (println "Sort:" (sort numbers))
    (println "Sort descending:" (sort > numbers))
    (println "Sort-by length:" (sort-by count ["aaa" "b" "cc"]))))

;; 16. REVERSE - Reverse sequence
(defn example-reverse []
  (let [numbers [1 2 3 4 5]]
    (println "Reverse:" (reverse numbers))
    (println "Rseq (reversible seq):" (rseq (vec numbers)))))

;; 17. SHUFFLE - Randomize order
(defn example-shuffle []
  (let [numbers [1 2 3 4 5]]
    (println "Shuffle:" (shuffle numbers))))

;; 18. RANDOM-SAMPLE - Random sample
(defn example-random-sample []
  (let [numbers (range 100)]
    (println "Random sample 10%:" (random-sample 0.1 numbers))
    (println "Random sample 5:" (take 5 (shuffle numbers)))))

;; 19. SEQUENCE - Convert to sequence
(defn example-sequence []
  (println "String to seq:" (sequence "hello"))
  (println "Array to seq:" (sequence (into-array [1 2 3]))))

;; 20. DOALL - Force evaluation
(defn example-doall []
  (let [lazy-seq (map #(do (println "Computing" %) (* % %)) (range 5))]
    (println "Before doall (lazy):")
    (println "After doall (evaluated):" (doall lazy-seq))))

;; 21. DORUN - Force evaluation for side effects
(defn example-dorun []
  (println "Dorun (no return):")
  (dorun (map #(println "Processing" %) [1 2 3])))

;; 22. LAZY-SEQ - Create custom lazy sequence
(defn example-lazy-seq []
  (defn natural-numbers
    ([] (natural-numbers 1))
    ([n] (lazy-seq (cons n (natural-numbers (inc n))))))
  (println "Natural numbers (first 10):" (take 10 (natural-numbers))))

;; 23. CHUNKED SEQUENCES - Efficient processing
(defn example-chunked []
  (let [numbers (range 1000)
        chunked (chunked-seq? numbers)]
    (println "Is chunked?" chunked)
    (println "First chunk:" (take 32 numbers))))

;; Run examples
(defn -main []
  (println "=== 06. SEQUENCES EXAMPLES ===\n")
  (example-range)
  (println)
  (example-repeat)
  (println)
  (example-iterate)
  (println)
  (example-lazy)
  (println)
  (example-take)
  (println)
  (example-drop)
  (println)
  (example-split-at)
  (println)
  (example-partition)
  (println)
  (example-interleave)
  (println)
  (example-concat)
  (println)
  (example-distinct)
  (println)
  (example-dedupe)
  (println)
  (example-frequencies)
  (println)
  (example-group-by)
  (println)
  (example-sort)
  (println)
  (example-reverse)
  (println)
  (example-shuffle)
  (println)
  (example-random-sample)
  (println)
  (example-sequence)
  (println)
  (example-doall)
  (println)
  (example-dorun)
  (println)
  (example-lazy-seq))

