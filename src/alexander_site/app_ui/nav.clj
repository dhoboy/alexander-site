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

(defn content [props]
  (let [home "/home"
        alexander "/alexander-technique"
        faculty "/faculty"
        contact "/contact"
        current (:req/uri props)]
    (ui/html
     [:ul.nav ;; me: what benefit does this being a ul with li's wrapping a's add to this?
      [:li
       [:a {:href home
            :class (when (= current home) "active")}
        (::home props)]]

      [:li
       [:a {:href alexander
            :class (when (= current alexander) "active")}
        (::alexander-technique props)]]

      [:li
       [:a {:href faculty
            :class (when (= current faculty) "active")}
        (::faculty props)]]

      [:li
       [:a {:href contact
            :class (when (= current contact) "active")}
        (::contact props)]]])))

(defstyles styles
  [:.nav {:font-family "Shippori Antique B1"
          :display "flex"
          :justify-content "space-around"
          :padding "20px 0 15px"
          :font-size "24px"}
   [:li {:cursor "pointer"}
    [:a {:text-decoration "none"
         :color colors/text-black}]
     [:a.active {:border-bottom "1px solid"}]]])


(def strings
  {::home "Home"
   ::alexander-technique "Alexander Technique"
   ::faculty "Faculty"
   ::contact "Contact"})

;;;; Controller

(defn nav [props]
  (-> props
      (ui/add-translated-strs strings)
      content))
