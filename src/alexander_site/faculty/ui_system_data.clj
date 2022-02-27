(ns alexander-site.faculty.ui-system-data
  (:require
    [alexander-site.faculty.faculty-screen :as faculty-screen]))

(def routes
  [["/faculty" faculty-screen/handler]])

(def strings
  (merge faculty-screen/strings))

(def styles
  (concat faculty-screen/styles))
