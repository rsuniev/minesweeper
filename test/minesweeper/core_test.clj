(ns minesweeper.core-test
  (:use [minesweeper.core] :reload-all)
  (:use [clojure.test]))

;;(defn board-fixture [f]
;;  (binding [test-board (create-board 2 2)]
;;    (f)))

;(defn board-fixture [f]
;  ((create-board 2 2)
;   (f)))

;(defn board-fixture [f]
;  (binding [test-board (create-board 2 2)]
;    (f)))

(defn board-fixture [f]
  (create-board 2 2)
  (f))

(use-fixtures :each board-fixture)

(deftest test-create-board
  (is (= @*board* [{:x 0 :y 0 :mine false}
                  {:x 0 :y 1 :mine false}
                  {:x 1 :y 0 :mine false}
                  {:x 1 :y 1 :mine false}])))

(deftest test-place-mine
  (is (= (place-mine 0 1) [{:x 0 :y 0 :mine false}
                   {:x 0 :y 1 :mine true}
                   {:x 1 :y 0 :mine false}
                   {:x 1 :y 1 :mine false}])))
                                        
(deftest test-mine-positions
  (is (= '(0 2) (mine-positions "*.*.")))
  (is (= '() (mine-positions "...."))))

(deftest test-placing-mine-via-command    
  (is (= [{:x 0 :y 0 :mine false}
          {:x 0 :y 1 :mine true}
          {:x 1 :y 0 :mine true}
          {:x 1 :y 1 :mine false}] (apply-mine-command ".*\n*.\n"))))
