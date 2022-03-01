(ns alexander-site.app-ui.header
  (:require
   [garden.core :as garden]
   [garden.color :refer [rgba]]
   [garden.def :refer [defstyles]]
   [garden.stylesheet :refer [at-media]]
   [garden.units :refer [percent]]
   [hiccup.page :as hp]
   [alexander-site.ui-system.ui :as ui]
   [alexander-site.app-ui.colors :as colors]))

;;;; View

;; Components are parts used on Screens

(defn content [props]
  "header component content"
  (ui/html
   [:h1 (::title props)]
   [:h2 (::sub-title props)]))

(def styles
  [[:header {:font-family "Shippori Antique B1"
            :background-color colors/header-blue
            :color colors/black
            :box-shadow "0px 1px 2px #ccc"
            :padding "20px"
            :text-align "center"}
   [:h2 {:font-family "Dancing Script"
         :font-size "35px"}]]])

(def strings
  {::title "Alexander Technique Training Center"
   ::sub-title "in Charlottesville"})

;;;; Controller

(defn header [props]
  "Exports the header component"
  (-> props
      (ui/add-translated-strs strings)
      content))

