(ns alexander-site.app-ui.nav
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

(defn li-nav-item
  "Renders out a nav item"
  [{:keys [href active display]}]
    [:li
      [:a {:href href
           :class (when (= active href) "active")}
        display]])

(defn content [props]
  "nav component content"
  (let [active (:req/uri props)]
    (ui/html
      [:ul.nav
        (li-nav-item
          {:href "/home"
           :active active
           :display (::home props)})
        (li-nav-item
          {:href "/alexander-technique"
           :active active
           :display (::alexander-technique props)})
        (li-nav-item
          {:href "/faculty"
           :active active
           :display (::faculty props)})
       (li-nav-item
         {:href "/contact"
          :active active
          :display (::contact props)})])))


(def styles
  [[:.nav {:font-family "Shippori Antique B1"
          :display "flex"
          :justify-content "space-around"
          :padding "20px 0 15px"
          :font-size "24px"}
   [:li {:cursor "pointer"}
    [:a {:text-decoration "none"
         :color colors/text-black}]
     [:a.active {:border-bottom "1px solid"}]]]])


(def strings
  {::home "Home"
   ::alexander-technique "Alexander Technique"
   ::faculty "Teachers"
   ::contact "Contact"})


;;;; Controller

(defn nav [props]
  "Exported nav component"
  (-> props
      (ui/add-translated-strs strings)
      content))
