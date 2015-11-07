(ns brute.example.signal
  (:require [viba.core :as viba]
            [brute.core :as brute]))

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
                  {:fill "#393" :opacity 0.5} (viba/average 1024 (take 1280 signal))))



