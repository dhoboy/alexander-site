(ns alexander-site.alexander-technique.ui-system-data
  (:require
    [alexander-site.alexander-technique.alexander-screen :as alexander-screen]))

(def routes
  [["/alexander-technique" alexander-screen/handler]])

(def strings
  (merge alexander-screen/strings))

(def styles
  (concat alexander-screen/styles))
