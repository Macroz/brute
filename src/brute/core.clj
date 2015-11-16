(ns brute.core
  (:require [hiccup.core :as h]))

(defn bar [width height middle i v]
  (let [x (* i width)
        y (- middle (* v height))
        y (if (< v 0) middle y)
        h (Math/abs (double (* v height)))]
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

(defn plot1 [extent attrs coll]
  (let [width (/ 1280 (count coll))
        height (/ 720 (:data-height extent))
        middle (:middle extent)]
    (bar-chart (merge {:fill "#777"} attrs) width height middle coll)))

(defn get-extent [coll]
  (let [n (count coll)
        width (/ 1280 n)
        miny (double (apply min coll))
        maxy (double (apply max coll))
        data-height (* 2.0 (Math/max (Math/abs (double miny)) (Math/abs (double maxy))))
        height (/ 720 data-height)]
    {:miny miny
     :maxy maxy
     :width width
     :height height
     :data-height data-height}))

(defn combine-extents [x1 x2]
  {:miny (Math/min (:miny x1) (:miny x2))
   :maxy (Math/max (:maxy x1) (:maxy x2))
   :data-height (Math/max (:data-height x1) (:data-height x2))})

(defn get-extents [& data]
  (let [extent (reduce combine-extents (map get-extent data))
        {:keys [miny maxy data-height]} extent
        middle (+ 360 (/ (* 720 (+ miny maxy)) data-height))]
    (assoc extent :middle middle)))

(defn plot [& plots]
  (wrap-svg {:style "margin: auto; height: 100%;"}
            (let [data (map second (partition 2 plots))
                  extent (apply get-extents data)]
              (map #(apply plot1 extent %) (partition 2 plots)))))
