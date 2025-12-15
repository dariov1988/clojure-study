(ns examples.09-protocols
  (:gen-class))

;; ============================================
;; LEVEL 3: PROTOCOLS - Polymorphism
;; ============================================

;; 1. DEFPROTOCOL - Define a protocol
(defprotocol Drawable
  "Protocol for drawable shapes"
  (draw [this] "Draw the shape")
  (area [this] "Calculate the area")
  (perimeter [this] "Calculate the perimeter"))

;; 2. EXTEND-TYPE - Extend protocol to existing type
(extend-type String
  Drawable
  (draw [this] (println "Drawing text:" this))
  (area [_this] 0)
  (perimeter [this] (* 2 (count this))))

(defn example-extend-type []
  (let [text "Hello"]
    (draw text)
    (println "Area:" (area text))
    (println "Perimeter:" (perimeter text))))

;; 3. EXTEND-PROTOCOL - Extend multiple types
(extend-protocol Drawable
  java.lang.Long
  (draw [this] (println "Drawing number:" this))
  (area [this] this)
  (perimeter [this] this))

;; 4. DEFRECORD - Define a record (like a struct)
(defrecord Circle [radius])

(extend-type Circle
  Drawable
  (draw [this] (println "Drawing circle with radius" (:radius this)))
  (area [this] (* Math/PI (:radius this) (:radius this)))
  (perimeter [this] (* 2 Math/PI (:radius this))))

(defn example-record []
  (let [circle (->Circle 5)]
    (draw circle)
    (println "Area:" (area circle))
    (println "Perimeter:" (perimeter circle))))

;; 5. DEFTYPE - Define a type (mutable)
(deftype Rectangle [width height]
  Drawable
  (draw [this] (println "Drawing rectangle" (:width this) "x" (:height this)))
  (area [this] (* (:width this) (:height this)))
  (perimeter [this] (* 2 (+ (:width this) (:height this)))))

(defn example-type []
  (let [rect (Rectangle. 10 20)]
    (draw rect)
    (println "Area:" (area rect))
    (println "Perimeter:" (perimeter rect))))

;; 6. RECORDS WITH PROTOCOL IMPLEMENTATION
(defrecord Square [side]
  Drawable
  (draw [this] (println "Drawing square with side" (:side this)))
  (area [this] (* (:side this) (:side this)))
  (perimeter [this] (* 4 (:side this))))

(defn example-square []
  (let [square (->Square 5)]
    (draw square)
    (println "Area:" (area square))
    (println "Perimeter:" (perimeter square))))

;; 7. MULTIMETHODS - Multiple dispatch
(defmulti describe (fn [x] (type x)))

(defmethod describe String [x]
  (str "String: " x))

(defmethod describe Long [x]
  (str "Number: " x))

(defmethod describe :default [x]
  (str "Unknown: " (type x)))

(defn example-multimethod []
  (println (describe "hello"))
  (println (describe 42))
  (println (describe :keyword)))

;; 8. MULTIMETHODS WITH CUSTOM DISPATCH
(defmulti process :type)

(defmethod process :email [msg]
  (str "Sending email: " (:content msg)))

(defmethod process :sms [msg]
  (str "Sending SMS: " (:content msg)))

(defmethod process :default [msg]
  (str "Unknown message type: " (:type msg)))

(defn example-multimethod-custom []
  (println (process {:type :email :content "Hello"}))
  (println (process {:type :sms :content "Hi"}))
  (println (process {:type :unknown :content "Test"})))

;; 9. MULTIMETHODS WITH HIERARCHY
(derive ::cat ::animal)
(derive ::dog ::animal)
(derive ::persian ::cat)
(derive ::siamese ::cat)

(defmulti speak :species)

(defmethod speak ::animal [_animal]
  "Some animal sound")

(defmethod speak ::cat [_animal]
  "Meow")

(defmethod speak ::dog [_animal]
  "Woof")

(defmethod speak ::persian [_animal]
  "Elegant meow")

(defn example-hierarchy []
  (println (speak {:species ::cat}))
  (println (speak {:species ::dog}))
  (println (speak {:species ::persian})))

;; 10. REIFY - Create anonymous implementation
(defn example-reify []
  (let [shape (reify Drawable
                 (draw [_this] (println "Drawing custom shape"))
                 (area [_this] 100)
                 (perimeter [_this] 40))]
    (draw shape)
    (println "Area:" (area shape))))

;; 11. PROTOCOL EXTENSION - Extend to nil
(extend-type nil
  Drawable
  (draw [_this] (println "Nothing to draw"))
  (area [_this] 0)
  (perimeter [_this] 0))

;; 12. SATISFIES? - Check if type satisfies protocol
(defn example-satisfies? []
  (let [circle (->Circle 5)
        square (->Square 4)]
    (println "Circle satisfies Drawable?" (satisfies? Drawable circle))
    (println "Square satisfies Drawable?" (satisfies? Drawable square))
    (println "String satisfies Drawable?" (satisfies? Drawable "test"))))

;; 13. MULTIPLE PROTOCOLS
(defprotocol Movable
  (move [this dx dy] "Move the object"))

(defprotocol Resizable
  (resize [this factor] "Resize the object"))

(defrecord Shape [x y width height]
  Drawable
  (draw [this] (println "Drawing shape at" (:x this) (:y this)))
  (area [this] (* (:width this) (:height this)))
  (perimeter [this] (* 2 (+ (:width this) (:height this))))
  Movable
  (move [this dx dy] (->Shape (+ (:x this) dx) (+ (:y this) dy)
                              (:width this) (:height this)))
  Resizable
  (resize [this factor] (->Shape (:x this) (:y this)
                                 (* (:width this) factor)
                                 (* (:height this) factor))))

(defn example-multiple-protocols []
  (let [shape (->Shape 0 0 10 20)]
    (draw shape)
    (let [moved (move shape 5 10)]
      (draw moved))
    (let [resized (resize shape 2)]
      (println "Resized area:" (area resized)))))

;; 14. PROTOCOL WITH OPTIONAL METHODS
(defprotocol Optional
  (required [this])
  (optional [this]))

(defrecord Impl1 []
  Optional
  (required [_this] "Required")
  (optional [_this] "Optional"))

;; 15. EXTEND WITH MAP
(extend Circle
  Movable
  {:move (fn [this _dx _dy]
           (->Circle (:radius this)))})

;; Run examples
(defn -main []
  (println "=== 09. PROTOCOLS EXAMPLES ===\n")
  (example-extend-type)
  (println)
  (example-record)
  (println)
  (example-type)
  (println)
  (example-square)
  (println)
  (example-multimethod)
  (println)
  (example-multimethod-custom)
  (println)
  (example-hierarchy)
  (println)
  (example-reify)
  (println)
  (example-satisfies?)
  (println)
  (example-multiple-protocols))

