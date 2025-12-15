(ns examples.08-concurrency
  (:gen-class))

;; ============================================
;; LEVEL 3: CONCURRENCY - Atoms, Refs, Agents
;; ============================================

;; 1. ATOMS - Uncoordinated, synchronous state
(defn example-atoms []
  (let [counter (atom 0)]
    (println "Initial value:" @counter)
    (swap! counter inc)
    (println "After inc:" @counter)
    (swap! counter + 10)
    (println "After add 10:" @counter)
    (reset! counter 100)
    (println "After reset:" @counter)
    (compare-and-set! counter 100 200)
    (println "After compare-and-set:" @counter)))

;; 2. ATOM WITH VALIDATOR
(defn example-atom-validator []
  (let [counter (atom 0 :validator pos?)]
    (try
      (swap! counter inc)
      (println "Valid update:" @counter)
      (swap! counter - 10) ; This will fail
      (catch IllegalStateException e
        (println "Validation failed:" (.getMessage e))))))

;; 3. ATOM WITH WATCHER
(defn example-atom-watcher []
  (let [counter (atom 0)
        watcher (fn [key _ref old new]
                  (println "Watcher:" key "changed from" old "to" new))]
    (add-watch counter :watcher watcher)
    (swap! counter inc)
    (swap! counter inc)
    (remove-watch counter :watcher)))

;; 4. REFS - Coordinated, synchronous state (STM)
(defn example-refs []
  (let [account1 (ref 100)
        account2 (ref 50)]
    (dosync
      (alter account1 - 20)
      (alter account2 + 20))
    (println "Account 1:" @account1)
    (println "Account 2:" @account2)))

;; 5. REFS WITH COMMUTE - Commutative operations
(defn example-refs-commute []
  (let [counter (ref 0)]
    (dosync
      (commute counter inc)
      (commute counter inc)
      (commute counter inc))
    (println "Counter:" @counter)))

;; 6. REFS WITH ENSURE - Prevent lost updates
(defn example-refs-ensure []
  (let [data (ref {:version 1 :value 100})]
    (dosync
      (ensure data) ; Ensure we have latest version
      (alter data update :version inc)
      (alter data update :value + 10))
    (println "Data:" @data)))

;; 7. AGENTS - Uncoordinated, asynchronous state
(defn example-agents []
  (let [agent-counter (agent 0)]
    (send agent-counter inc)
    (send agent-counter + 10)
    (send agent-counter * 2)
    (await agent-counter) ; Wait for all actions to complete
    (println "Agent counter:" @agent-counter)))

;; 8. AGENTS WITH ERROR HANDLING
(defn example-agents-errors []
  (let [agent-counter (agent 0 :error-handler 
                             (fn [agent err]
                               (println "Error:" (.getMessage err))
                               (reset! agent 0)))]
    (send agent-counter / 0) ; This will cause error
    (await agent-counter)
    (println "Agent after error:" @agent-counter)))

;; 9. AGENTS WITH VALIDATOR
(defn example-agents-validator []
  (let [agent-counter (agent 0 :validator pos?)]
    (send agent-counter inc)
    (await agent-counter)
    (println "Valid update:" @agent-counter)
    (try
      (send agent-counter - 10)
      (await agent-counter)
      (catch Exception e
        (println "Validation error:" (.getMessage e))))))

;; 10. FUTURES - Asynchronous computation
(defn example-futures []
  (let [future-result (future
                        (Thread/sleep 1000)
                        (println "Future completed")
                        (+ 10 20))]
    (println "Doing other work...")
    (println "Future result:" @future-result)))

;; 11. PROMISES - One-time value delivery
(defn example-promises []
  (let [promise-result (promise)]
    (future
      (Thread/sleep 1000)
      (deliver promise-result "Hello from promise"))
    (println "Waiting for promise...")
    (println "Promise result:" @promise-result)))

;; 12. DELAY - Lazy evaluation
(defn example-delay []
  (let [delayed-value (delay
                        (println "Computing delayed value")
                        (+ 10 20))]
    (println "Delay created, not computed yet")
    (println "First access:" @delayed-value)
    (println "Second access:" @delayed-value)))

;; 13. PMAP - Parallel map
(defn example-pmap []
  (let [numbers (range 1000)
        slow-fn (fn [x] (Thread/sleep 10) (* x x))]
    (time (doall (map slow-fn (take 10 numbers))))
    (time (doall (pmap slow-fn (take 10 numbers))))))

;; 14. PCALL - Parallel function calls
(defn example-pcall []
  (let [futures [(future (Thread/sleep 100) 1)
                 (future (Thread/sleep 100) 2)
                 (future (Thread/sleep 100) 3)]
        result (map deref futures)]
    (println "Pcall result:" result)))

;; 15. ATOMS FOR COUNTERS
(defn example-atom-counter []
  (let [counter (atom 0)
        incrementers (repeatedly 10 #(future (swap! counter inc)))]
    (doseq [f incrementers] @f)
    (println "Final counter value:" @counter)))

;; 16. REFS FOR TRANSACTIONS
(defn example-ref-transaction []
  (let [balance (ref 100)
        withdraw (fn [amount]
                   (dosync
                     (if (>= @balance amount)
                       (alter balance - amount)
                       (throw (Exception. "Insufficient funds")))))]
    (try
      (withdraw 30)
      (println "Balance after withdrawal:" @balance)
      (withdraw 100) ; This will fail
      (catch Exception e
        (println "Error:" (.getMessage e))
        (println "Balance unchanged:" @balance)))))

;; 17. AGENTS FOR ASYNC UPDATES
(defn example-agent-async []
  (let [log-agent (agent [])]
    (doseq [_i (range 5)]
      (send log-agent (fn [logs] (conj logs (str "Event " (count logs))))))
    (await log-agent)
    (println "Log entries:" @log-agent)))

;; 18. COMBINING CONCURRENCY PRIMITIVES
(defn example-combined []
  (let [counter (atom 0)
        results (ref [])
        logger (agent [])]
    (dosync
      (doseq [_i (range 5)]
        (let [value (swap! counter inc)]
          (alter results conj value)
          (send logger (fn [logs] (conj logs (str "Counted: " value)))))))
    (await logger)
    (println "Counter:" @counter)
    (println "Results:" @results)
    (println "Logs:" @logger)))

;; 19. VOLATILE - Fast, unsynchronized mutable container
(defn example-volatile []
  (let [v (volatile! 0)]
    (vswap! v inc)
    (vswap! v + 10)
    (println "Volatile value:" @v)))

;; 20. ATOMIC LONG - For numeric operations
(defn example-atomic []
  (let [atomic-counter (java.util.concurrent.atomic.AtomicLong. 0)]
    (.incrementAndGet atomic-counter)
    (.addAndGet atomic-counter 10)
    (println "Atomic counter:" (.get atomic-counter))))

;; Run examples
(defn -main []
  (println "=== 08. CONCURRENCY EXAMPLES ===\n")
  (example-atoms)
  (println)
  (example-atom-validator)
  (println)
  (example-atom-watcher)
  (println)
  (example-refs)
  (println)
  (example-refs-commute)
  (println)
  (example-refs-ensure)
  (println)
  (example-agents)
  (println)
  (example-agents-errors)
  (println)
  (example-futures)
  (println)
  (example-promises)
  (println)
  (example-delay)
  (println)
  (example-atom-counter)
  (println)
  (example-ref-transaction)
  (println)
  (example-agent-async)
  (println)
  (example-combined)
  (println)
  (example-volatile))

