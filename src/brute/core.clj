(ns brute.core
  (:require [hiccup.core :as h]))

(defn bar [width height middle i v]
  (let [x (* i width)
        y (- middle (* v height))
        h (Math/abs (* v height))
        y (if (< v 0) middle y)]
    [:rect {:x x :y y :width width :height h}]))

(defn bar-chart [attrs width height middle coll]
  (into [:g (merge {:fill "black" :stroke 0 :stroke-color "black"} attrs)]
        (map-indexed (partial bar width height middle) coll)))

(defn wrap-svg [attrs charts]
  (h/html
   (into [:svg (merge {:xmlns "http://www.w3.org/2000/svg"
                       :width 1280 :height 720
                       :viewBox "0,0,1280,720"}
                      attrs)]
         charts)))

(defn plot1 [attrs coll]
  (let [n (count coll)
        width (/ 1280 n)
        miny (apply min coll)
        maxy (apply max coll)
        height (/ 720 (Math/abs (- maxy miny)))
        middle (* (/ (Math/abs maxy) (Math/abs (- maxy miny))) 720)]
    (bar-chart (merge {:fill "#777"} attrs) width height middle coll)))

(defn plot [& plots]
  (wrap-svg {:style "margin: auto; height: 100%;"}
            (map #(apply plot1 %) (partition 2 plots))))
