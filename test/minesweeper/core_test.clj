(ns minesweeper.core-test
  (:use [minesweeper.core] :reload-all)
  (:use [clojure.test]))

;(load-board 2 2)

(defn clean-board [board]
  (board []))

(defn boards-fixture [f]
  (load-board 2 2)
  (f)
  (alter-var-root #'board clean-board))

(use-fixtures :each boards-fixture)

(deftest test-load-board
  (is (= [{:x 0 :y 0 :mine false}
          {:x 0 :y 1 :mine false}
          {:x 1 :y 0 :mine false}
          {:x 1 :y 1 :mine false}] board)))

(deftest test-find-cell
  (is (= {:x 1 :y 0 :mine false} (find-cell 1 0)))
  (is (= {:x 1 :y 1 :mine false} (find-cell 1 1))))

(deftest test-place-mine
  (is (= {:x 1 :y 0 :mine true} (place-mine 0 1))))

(deftest test-load-board2
  (is (= [{:x 0 :y 0 :mine false}
          {:x 0 :y 1 :mine false}
          {:x 1 :y 0 :mine false}
          {:x 1 :y 1 :mine false}] board)))

(deftest test-mine-positions
  (is (= '(0 2) (mine-positions "*.*."))))


;(deftest test-placing-mine-via-command
;  (is (= ".." (apply-mine-command ".*\n*.\n"))))

(deftest test-mine-to-board
  (mine-to-board 1 1)
  (is (= [{:x 0 :y 0 :mine false}
          {:x 0 :y 1 :mine false}
          {:x 1 :y 0 :mine false}
          {:x 1 :y 1 :mine true}] board)))

;(deftest test-placing-mine-via-command    
;  (is (= [{:x 0 :y 0 :mine false}
;          {:x 0 :y 1 :mine true}
;          {:x 1 :y 0 :mine true}
;          {:x 1 :y 1 :mine false}] (apply-mine-command ".*\n*.\n"))))
