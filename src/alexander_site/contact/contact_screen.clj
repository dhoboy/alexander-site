(ns alexander-site.contact.contact-screen
  "Contact screen view and controller."
  (:require
   [alexander-site.app-ui.screen :as screen]
   [alexander-site.ui-system.ui :as ui]
   [garden.def :refer [defstyles]]
   [ring.util.response :as rr]))

;;;; View

(defn content [props]
  (ui/html
   [:div.contact-screen
    [:h3 "We want to hear from you!"]
    [:div.details
     [:p "You can reach us here -"]
     [:ul
      [:li "Phone: " (::phone props)]
      [:li "Email: " (::email props)]
      [:li "Address: " (::street props)]]]]))

(def strings
  {::html.doc.title.fragment "Contact"
   ::phone "(434) 960-8490"
   ::email "attcincville@gmail.com"
   ::street "1214 East Jefferson Street, Charlottesville, VA 22902"})

(defstyles styles
  [:.contact-screen
   [:.details {:padding-top "15px"}
    [:p {:font-weight "bold"}]
    [:ul {:list-style "initial"
          :margin "5px 0"
          :padding-left "22px"}
    [:li {:padding-bottom "5px"}]]]])

;;;; Controller

(defn handler [req]
  (-> req
      (screen/render content strings)
      rr/response
      (rr/content-type "text/html")))
