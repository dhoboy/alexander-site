(ns alexander-site.home.ui-system-data
  (:require
    [ring.util.response :as rr]
    [alexander-site.home.home-screen :as home-screen]))

(def routes
  [["/" home-screen/handler]
   ["/home" home-screen/handler]])

(def strings
  (merge home-screen/strings))

(def styles
  (concat home-screen/styles))
