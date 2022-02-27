(ns alexander-site.app-ui.ui-system-data
  (:require
   [alexander-site.app-ui.screen :as screen]
   [alexander-site.app-ui.header :as header]
   [alexander-site.app-ui.nav :as nav]))

(def routes
  nil)

(def strings
  (merge
   header/strings
   nav/strings
   screen/strings))

(def styles
  (concat
   header/styles
   nav/styles
   screen/styles))


