(ns minesweeper.core
  (:use [clojure.contrib.seq :only [positions]]))

(def *board* (ref []))

(defn create-board [n m]
  (dosync
   (ref-set *board*
          (vec (flatten
                (for [x (range n) y (range m)] {:x x :y y :mine false}))))))
 
(defn place-mine [x y]
  (dosync
   (alter *board*
          (partial clojure.walk/walk
                   #(if (= (map % [:x :y]) [x y])
                      (assoc % :mine true) %) identity))))

(defn mine-positions [mines-str]
  (positions #(= \* %) (seq mines-str)))

(defn apply-mine-command [command]
    (let [mines (map #(mine-positions %) (.split command "\n"))]
      (for [i (range (count mines))]
        (map (partial place-mine i) (nth mines i)))
      @*board*))
