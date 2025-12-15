(ns examples.11-recursion-interview
  (:require [clojure.string :as str])
  (:gen-class))

;; ============================================
;; RECURSION INTERVIEW EXERCISES
;; Common recursion problems well-resolved in Clojure
;; ============================================

;; ============================================
;; 1. FIBONACCI SEQUENCE
;; ============================================

;; Naive recursive (exponential time - for demonstration)
(defn fib-naive
  "Naive recursive Fibonacci - O(2^n) time complexity"
  [n]
  (if (< n 2)
    n
    (+ (fib-naive (dec n))
       (fib-naive (- n 2)))))

;; Tail-recursive with accumulator (O(n) time, O(1) space)
(defn fib-tail-recursive
  "Tail-recursive Fibonacci - O(n) time, O(1) space"
  [n]
  (letfn [(fib-helper [a b count]
            (if (zero? count)
              a
              (recur b (+ a b) (dec count))))]
    (fib-helper 0 1 n)))

;; Memoized version (O(n) time, O(n) space)
(def fib-memoized
  (memoize (fn [n]
             (if (< n 2)
               n
               (+ (fib-memoized (dec n))
                  (fib-memoized (- n 2)))))))

;; Iterative approach (O(n) time, O(1) space)
(defn fib-iterative
  "Iterative Fibonacci - O(n) time, O(1) space"
  [n]
  (loop [a 0 b 1 count n]
    (if (zero? count)
      a
      (recur b (+ a b) (dec count)))))

(defn example-fibonacci []
  (println "=== Fibonacci Examples ===")
  (println "Naive fib(10):" (fib-naive 10))
  (println "Tail-recursive fib(10):" (fib-tail-recursive 10))
  (println "Memoized fib(30):" (fib-memoized 30))
  (println "Iterative fib(10):" (fib-iterative 10))
  (println "First 10 Fibonacci numbers:"
           (map fib-tail-recursive (range 10))))

;; ============================================
;; 2. FACTORIAL
;; ============================================

;; Simple recursive
(defn factorial
  "Calculate n! recursively"
  [n]
  (if (<= n 1)
    1
    (* n (factorial (dec n)))))

;; Tail-recursive
(defn factorial-tail
  "Tail-recursive factorial"
  [n]
  (letfn [(fact-helper [acc n]
            (if (<= n 1)
              acc
              (recur (* acc n) (dec n))))]
    (fact-helper 1 n)))

(defn example-factorial []
  (println "=== Factorial Examples ===")
  (println "5! = " (factorial 5))
  (println "10! = " (factorial-tail 10)))

;; ============================================
;; 3. BINARY SEARCH
;; ============================================

(defn binary-search
  "Binary search in sorted collection - O(log n)"
  [coll target]
  (letfn [(search [low high]
            (when (<= low high)
              (let [mid (quot (+ low high) 2)
                    mid-val (nth coll mid)]
                (cond
                  (= mid-val target) mid
                  (< mid-val target) (search (inc mid) high)
                  :else (search low (dec mid))))))]
    (search 0 (dec (count coll)))))

(defn example-binary-search []
  (println "=== Binary Search Examples ===")
  (let [sorted-vec [1 3 5 7 9 11 13 15 17 19]]
    (println "Searching for 7 in" sorted-vec)
    (println "Index:" (binary-search sorted-vec 7))
    (println "Searching for 10 in" sorted-vec)
    (println "Index:" (binary-search sorted-vec 10))))

;; ============================================
;; 4. REVERSE A LIST/STRING
;; ============================================

;; Recursive reverse
(defn reverse-recursive
  "Recursively reverse a collection"
  [coll]
  (if (empty? coll)
    coll
    (conj (reverse-recursive (rest coll)) (first coll))))

;; Tail-recursive reverse
(defn reverse-tail
  "Tail-recursive reverse"
  [coll]
  (letfn [(rev-helper [acc coll]
            (if (empty? coll)
              acc
              (recur (conj acc (first coll)) (rest coll))))]
    (rev-helper [] coll)))

(defn example-reverse []
  (println "=== Reverse Examples ===")
  (println "Reverse [1 2 3 4 5]:" (reverse-recursive [1 2 3 4 5]))
  (println "Reverse tail-recursive:" (reverse-tail [1 2 3 4 5]))
  (println "Reverse string:" (apply str (reverse-recursive "hello"))))

;; ============================================
;; 5. PALINDROME CHECK
;; ============================================

(defn palindrome?
  "Check if string is palindrome recursively"
  [s]
  (let [s (str/lower-case (str/replace s #"[^a-zA-Z0-9]" ""))]
    (letfn [(pal-helper [s]
              (if (<= (count s) 1)
                true
                (and (= (first s) (last s))
                     (pal-helper (subs s 1 (dec (count s)))))))]
      (pal-helper s))))

(defn example-palindrome []
  (println "=== Palindrome Examples ===")
  (println "\"racecar\" is palindrome?" (palindrome? "racecar"))
  (println "\"hello\" is palindrome?" (palindrome? "hello"))
  (println "\"A man a plan a canal Panama\" is palindrome?" 
           (palindrome? "A man a plan a canal Panama")))

;; ============================================
;; 6. POWER FUNCTION
;; ============================================

;; Naive recursive (O(n))
(defn power-naive
  "Calculate base^exp - O(n)"
  [base exp]
  (if (zero? exp)
    1
    (* base (power-naive base (dec exp)))))

;; Optimized recursive (O(log n))
(defn power-optimized
  "Calculate base^exp using exponentiation by squaring - O(log n)"
  [base exp]
  (cond
    (zero? exp) 1
    (even? exp) (let [half (power-optimized base (quot exp 2))]
                  (* half half))
    :else (* base (power-optimized base (dec exp)))))

(defn example-power []
  (println "=== Power Examples ===")
  (println "2^10 (naive):" (power-naive 2 10))
  (println "2^10 (optimized):" (power-optimized 2 10))
  (println "3^5:" (power-optimized 3 5)))

;; ============================================
;; 7. GREATEST COMMON DIVISOR (GCD)
;; ============================================

(defn gcd
  "Calculate GCD using Euclidean algorithm"
  [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))

(defn example-gcd []
  (println "=== GCD Examples ===")
  (println "GCD(48, 18):" (gcd 48 18))
  (println "GCD(100, 25):" (gcd 100 25))
  (println "GCD(17, 13):" (gcd 17 13)))

;; ============================================
;; 8. TOWER OF HANOI
;; ============================================

(defn tower-of-hanoi
  "Solve Tower of Hanoi puzzle recursively"
  [n from to aux]
  (when (> n 0)
    (tower-of-hanoi (dec n) from aux to)
    (println (str "Move disk " n " from " from " to " to))
    (tower-of-hanoi (dec n) aux to from)))

(defn example-tower-of-hanoi []
  (println "=== Tower of Hanoi (3 disks) ===")
  (tower-of-hanoi 3 "A" "C" "B"))

;; ============================================
;; 9. MERGE SORT
;; ============================================

(defn merge-sort
  "Merge sort algorithm - O(n log n)"
  [coll]
  (letfn [(merge-lists [left right]
            (cond
              (empty? left) right
              (empty? right) left
              (<= (first left) (first right))
              (cons (first left) (merge-lists (rest left) right))
              :else
              (cons (first right) (merge-lists left (rest right)))))
          
          (split [coll]
            (let [mid (quot (count coll) 2)]
              [(take mid coll) (drop mid coll)]))]
    
    (if (<= (count coll) 1)
      coll
      (let [[left right] (split coll)]
        (merge-lists (merge-sort left) (merge-sort right))))))

(defn example-merge-sort []
  (println "=== Merge Sort Examples ===")
  (let [unsorted [64 34 25 12 22 11 90 5]]
    (println "Unsorted:" unsorted)
    (println "Sorted:" (merge-sort unsorted))))

;; ============================================
;; 10. QUICK SORT
;; ============================================

(defn quick-sort
  "Quick sort algorithm - O(n log n) average, O(n^2) worst"
  [coll]
  (if (<= (count coll) 1)
    coll
    (let [pivot (first coll)
          rest-coll (rest coll)
          smaller (filter #(< % pivot) rest-coll)
          equal (filter #(= % pivot) rest-coll)
          larger (filter #(> % pivot) rest-coll)]
      (concat (quick-sort smaller)
              equal
              (quick-sort larger)))))

(defn example-quick-sort []
  (println "=== Quick Sort Examples ===")
  (let [unsorted [64 34 25 12 22 11 90 5]]
    (println "Unsorted:" unsorted)
    (println "Sorted:" (quick-sort unsorted))))

;; ============================================
;; 11. BINARY TREE OPERATIONS
;; ============================================

;; Tree structure: {:value v :left l :right r} or nil

(defn tree-depth
  "Calculate depth of binary tree"
  [tree]
  (if (nil? tree)
    0
    (inc (max (tree-depth (:left tree))
              (tree-depth (:right tree))))))

(defn tree-size
  "Count nodes in binary tree"
  [tree]
  (if (nil? tree)
    0
    (inc (+ (tree-size (:left tree))
            (tree-size (:right tree))))))

(defn tree-sum
  "Sum all values in binary tree"
  [tree]
  (if (nil? tree)
    0
    (+ (:value tree)
       (tree-sum (:left tree))
       (tree-sum (:right tree)))))

(defn tree-inorder
  "In-order traversal: left, root, right"
  [tree]
  (when tree
    (concat (tree-inorder (:left tree))
            [(:value tree)]
            (tree-inorder (:right tree)))))

(defn tree-preorder
  "Pre-order traversal: root, left, right"
  [tree]
  (when tree
    (concat [(:value tree)]
            (tree-preorder (:left tree))
            (tree-preorder (:right tree)))))

(defn tree-postorder
  "Post-order traversal: left, right, root"
  [tree]
  (when tree
    (concat (tree-postorder (:left tree))
            (tree-postorder (:right tree))
            [(:value tree)])))

(defn example-binary-tree []
  (println "=== Binary Tree Examples ===")
  (let [tree {:value 1
              :left {:value 2
                     :left {:value 4 :left nil :right nil}
                     :right {:value 5 :left nil :right nil}}
              :right {:value 3
                      :left {:value 6 :left nil :right nil}
                      :right nil}}]
    (println "Tree depth:" (tree-depth tree))
    (println "Tree size:" (tree-size tree))
    (println "Tree sum:" (tree-sum tree))
    (println "In-order:" (tree-inorder tree))
    (println "Pre-order:" (tree-preorder tree))
    (println "Post-order:" (tree-postorder tree))))

;; ============================================
;; 12. PATH SUM IN TREE
;; ============================================

(defn has-path-sum?
  "Check if there's a path from root to leaf with given sum"
  [tree target-sum]
  (cond
    (nil? tree) false
    (and (nil? (:left tree)) (nil? (:right tree)))
    (= target-sum (:value tree))
    :else
    (let [remaining (- target-sum (:value tree))]
      (or (has-path-sum? (:left tree) remaining)
          (has-path-sum? (:right tree) remaining)))))

(defn example-path-sum []
  (println "=== Path Sum Examples ===")
  (let [tree {:value 5
              :left {:value 4
                     :left {:value 11
                            :left {:value 7 :left nil :right nil}
                            :right {:value 2 :left nil :right nil}}
                     :right nil}
              :right {:value 8
                      :left {:value 13 :left nil :right nil}
                      :right {:value 4
                              :right {:value 1 :left nil :right nil}}}}]
    (println "Has path sum 22?" (has-path-sum? tree 22))
    (println "Has path sum 27?" (has-path-sum? tree 27))
    (println "Has path sum 18?" (has-path-sum? tree 18))))

;; ============================================
;; 13. PERMUTATIONS
;; ============================================

(defn permutations
  "Generate all permutations of a collection"
  [coll]
  (if (<= (count coll) 1)
    [coll]
    (for [x coll
          perm (permutations (remove #(= % x) coll))]
      (cons x perm))))

(defn example-permutations []
  (println "=== Permutations Examples ===")
  (println "Permutations of [1 2 3]:")
  (doseq [perm (permutations [1 2 3])]
    (println "  " perm))
  (println "Count:" (count (permutations [1 2 3]))))

;; ============================================
;; 14. COMBINATIONS
;; ============================================

(defn combinations
  "Generate all combinations of n elements from collection"
  [n coll]
  (cond
    (zero? n) [[]]
    (empty? coll) []
    :else
    (let [x (first coll)
          rest-coll (rest coll)]
      (concat (map #(cons x %) (combinations (dec n) rest-coll))
              (combinations n rest-coll)))))

(defn example-combinations []
  (println "=== Combinations Examples ===")
  (println "Combinations of 2 from [1 2 3 4]:")
  (doseq [comb (combinations 2 [1 2 3 4])]
    (println "  " comb))
  (println "Count:" (count (combinations 2 [1 2 3 4]))))

;; ============================================
;; 15. FLOOD FILL
;; ============================================

(defn flood-fill
  "Flood fill algorithm (like paint bucket tool)"
  [matrix row col new-color]
  (let [old-color (get-in matrix [row col])
        rows (count matrix)
        cols (count (first matrix))]
    (if (or (nil? old-color) (= old-color new-color))
      matrix
      (letfn [(fill-helper [m r c]
                (if (and (>= r 0) (< r rows)
                        (>= c 0) (< c cols)
                        (= (get-in m [r c]) old-color)
                        (not= (get-in m [r c]) new-color))
                  (let [updated (assoc-in m [r c] new-color)]
                    (-> updated
                        (fill-helper (dec r) c)
                        (fill-helper (inc r) c)
                        (fill-helper r (dec c))
                        (fill-helper r (inc c))))
                  m))]
        (fill-helper matrix row col)))))

(defn example-flood-fill []
  (println "=== Flood Fill Examples ===")
  (let [matrix [[1 1 1 1 0]
                [1 1 1 0 0]
                [1 0 0 0 0]
                [1 0 0 0 0]]]
    (println "Original matrix:")
    (doseq [row matrix] (println "  " row))
    (println "After flood fill at (0,0) with color 2:")
    (let [filled (flood-fill matrix 0 0 2)]
      (doseq [row filled] (println "  " row)))))

;; ============================================
;; 16. COUNT ISLANDS (DFS)
;; ============================================

(defn count-islands
  "Count number of islands (connected 1s) using DFS"
  [matrix]
  (let [rows (count matrix)
        cols (count (first matrix))
        visited (atom #{})]
    (letfn [(dfs [r c]
              (when (and (>= r 0) (< r rows)
                        (>= c 0) (< c cols)
                        (= (get-in matrix [r c]) 1)
                        (not (contains? @visited [r c])))
                (swap! visited conj [r c])
                (dfs (dec r) c)
                (dfs (inc r) c)
                (dfs r (dec c))
                (dfs r (inc c))))]
      (loop [r 0 c 0 islands 0]
        (cond
          (>= r rows) islands
          (>= c cols) (recur (inc r) 0 islands)
          (and (= (get-in matrix [r c]) 1)
               (not (contains? @visited [r c])))
          (do (dfs r c)
              (recur r (inc c) (inc islands)))
          :else (recur r (inc c) islands))))))

(defn example-count-islands []
  (println "=== Count Islands Examples ===")
  (let [matrix [[1 1 0 0 0]
                [1 1 0 0 0]
                [0 0 1 0 0]
                [0 0 0 1 1]]]
    (println "Matrix:")
    (doseq [row matrix] (println "  " row))
    (println "Number of islands:" (count-islands matrix))))

;; ============================================
;; 17. VALIDATE BINARY SEARCH TREE
;; ============================================

(defn valid-bst?
  "Check if binary tree is a valid BST"
  [tree]
  (letfn [(validate [node min-val max-val]
            (or (nil? node)
                (and (or (nil? min-val) (>= (:value node) min-val))
                     (or (nil? max-val) (<= (:value node) max-val))
                     (validate (:left node) min-val (:value node))
                     (validate (:right node) (:value node) max-val))))]
    (validate tree nil nil)))

(defn example-valid-bst []
  (println "=== Valid BST Examples ===")
  (let [valid-tree {:value 5
                    :left {:value 3
                           :left {:value 2 :left nil :right nil}
                           :right {:value 4 :left nil :right nil}}
                    :right {:value 7
                            :left {:value 6 :left nil :right nil}
                            :right {:value 8 :left nil :right nil}}}
        invalid-tree {:value 5
                      :left {:value 6 :left nil :right nil}
                      :right {:value 7 :left nil :right nil}}]
    (println "Valid BST?" (valid-bst? valid-tree))
    (println "Invalid BST?" (valid-bst? invalid-tree))))

;; ============================================
;; 18. NTH FIBONACCI WITH MATRIX EXPONENTIATION
;; ============================================

(defn matrix-multiply
  "Multiply two 2x2 matrices"
  [[a b c d] [e f g h]]
  [ (+ (* a e) (* b g)) (+ (* a f) (* b h))
    (+ (* c e) (* d g)) (+ (* c f) (* d h))])

(defn matrix-power
  "Raise 2x2 matrix to power n using exponentiation by squaring"
  [matrix n]
  (cond
    (zero? n) [1 0 0 1]  ; Identity matrix
    (= n 1) matrix
    (even? n) (let [half (matrix-power matrix (quot n 2))]
                (matrix-multiply half half))
    :else (matrix-multiply matrix (matrix-power matrix (dec n)))))

(defn fib-matrix
  "Calculate nth Fibonacci using matrix exponentiation - O(log n)"
  [n]
  (let [fib-matrix [1 1 1 0]
        result (matrix-power fib-matrix n)]
    (first result)))

(defn example-fib-matrix []
  (println "=== Fibonacci with Matrix Exponentiation ===")
  (println "fib(10) using matrix:" (fib-matrix 10))
  (println "fib(20) using matrix:" (fib-matrix 20))
  (println "fib(30) using matrix:" (fib-matrix 30)))

;; Run all examples
(defn -main []
  (println "=== 11. RECURSION INTERVIEW EXERCISES ===\n")
  (example-fibonacci)
  (println)
  (example-factorial)
  (println)
  (example-binary-search)
  (println)
  (example-reverse)
  (println)
  (example-palindrome)
  (println)
  (example-power)
  (println)
  (example-gcd)
  (println)
  (example-tower-of-hanoi)
  (println)
  (example-merge-sort)
  (println)
  (example-quick-sort)
  (println)
  (example-binary-tree)
  (println)
  (example-path-sum)
  (println)
  (example-permutations)
  (println)
  (example-combinations)
  (println)
  (example-flood-fill)
  (println)
  (example-count-islands)
  (println)
  (example-valid-bst)
  (println)
  (example-fib-matrix))

