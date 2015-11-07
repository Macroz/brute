(ns brute.core
  (:require [hiccup.core :as h]))

(defn bar [width height middle i v]
  (let [x (* i width)
        y (- middle (* v height))
        y (if (< v 0) middle y)
        h (Math/abs (* v height))]
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
        miny (double (apply min coll))
        maxy (double (apply max coll))
        data-height (* 2.0 (Math/max (Math/abs miny) (Math/abs maxy)))
        height (/ 720 data-height)
        middle 360]
    (bar-chart (merge {:fill "#777"} attrs) width height middle coll)))

(defn plot [& plots]
  (wrap-svg {:style "margin: auto; height: 100%;"}
            (map #(apply plot1 %) (partition 2 plots))))
