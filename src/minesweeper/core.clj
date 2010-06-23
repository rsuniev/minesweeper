(ns minesweeper.core
  (:use [clojure.contrib.seq :only [positions]]))

(def board [])

(defn- generate-board [board n m]
  (vec (flatten
        (for [x (range n) y (range m)]
          (conj board {:x x :y y :mine false})))))
  
(defn load-board [n m]
  (alter-var-root #'board generate-board n m))

(defn find-cell [x y]
  (some #(when (= (map % [:x :y]) [x y]) %) board))

(defn place-mine [line-num mine-position]
  (assoc (find-cell mine-position line-num) :mine true))


(defn add-mine [board line-num mine-position]
  (let [updated-cell
        (assoc
            (some #(when (= (map % [:x :y])
                            [mine-position line-num]) %) board) :mine true)]
    (conj board updated-cell)))

(defn remove-mine [board x y]
  (remove #(= (map % [:x :y :mine]) [x y false]) board))
 
(defn mine-to-board [y x]
  (alter-var-root #'board add-mine y x)
  (alter-var-root #'board remove-mine x y))

(defn mine-positions [mines-str]
  (positions #(= \* %) (seq mines-str)))

(defn apply-mine-command [command]
    (let [mines (map #(mine-positions %) (.split command "\n"))]
      (for [i (range (count mines))]
        (map (partial mine-to-board i) (nth mines i)))
      board))
