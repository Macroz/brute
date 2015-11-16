(ns brute.example.signal
  (:require [brute.core :as brute]))

(defn- low-pass
  ([^floats arr]
   (let [result (float-array (alength arr))
         l (int (dec (alength arr)))
         r (int (dec l))]
     (aset result 0 (aget arr 0))
     (aset result 1 (aget arr 1))
     (aset result l (aget arr l))
     (aset result r (aget arr r))
     (loop [i 2]
       (if (= i r)
         (floats result)
         (do
           (aset result i (* (+ (aget arr (dec (dec i)))
                                (aget arr (dec i))
                                (aget arr i)
                                (aget arr (inc i))
                                (aget arr (inc (inc i)))
                                )
                             0.2))
           (recur (inc i)))))))
  ([^floats arr n]
   (loop [i (int n)
          arr (floats arr)]
     (if (= i 0)
       arr
       (recur (dec i) (low-pass arr))))))

(defn average [n coll]
  (seq (low-pass (float-array coll) n)))

(def signal
  (map (fn [x]
         (let [x (+ 0.01 x)]
           (* (/ 19.0 (Math/pow x 0.331))
              (+ (* 1.21 (Math/sin (* 381.1 x)))
                 (* 2.71 (Math/sin (* 0.5 x)))
                 (* 1.70 (Math/sin (* 2.7 x)))
                 (* 0.92 (Math/sin (* 11.3 x)))
                 (* 0.50 (Math/sin (* 32.3 x)))
                 (* 0.13 (Math/sin (* 123.7 x)))
                 ))))
       (map #(+ 2.3 (* 0.01 %)) (range))))

(spit "example/signal.svg"
      (brute/plot {} (take 1280 signal)
                  {:fill "#393" :opacity 0.5} (average 1024 (take 1280 signal))))



