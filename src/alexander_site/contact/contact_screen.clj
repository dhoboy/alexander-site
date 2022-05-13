(ns alexander-site.contact.contact-screen
  "Contact screen view and controller."
  (:require
   [alexander-site.app-ui.screen :as screen]
   [alexander-site.ui-system.ui :as ui]
   [garden.def :refer [defstyles]]
   [garden.stylesheet :refer [at-media]]
   [ring.util.response :as rr]))

;;;; View

;; Screens render entire pages

(defn content [props]
  "Contact Screen content"
  (ui/html
   [:div.contact-screen
     [:h3 "We want to hear from you!"]
     [:div.details
       [:div
         [:p
          [:span "Garnett"]
          [:span (::garnett-email props)]]
         [:p
           [:span "Lydia"]
           [:span (::lydia-email props)]]
         [:p
           [:span "Address"]
           [:span (::street props)]]]]]))

(def strings
  {::html.doc.title.fragment "Contact"
   ::garnett-email "garnettmellen@gmail.com"
   ::lydia-email "lydiavonbriesen@gmail.com"
   ::street "1214 East Jefferson Street, Charlottesville, VA 22902"})

(def styles
  [[:.contact-screen
   [:.details {:padding-top "15px"}
    [:p {:display "grid"
         :grid-template-columns "1fr 3fr"
         :padding "5px"}
     [:span:first-child {:color "#000"}]]
   (at-media {:min-width "992px"}
     [:p {:grid-template-columns "1fr 5fr"}])]]])

;;;; Controller

(defn handler [req]
  "Exported Contact Screen"
  (-> req
      (screen/render content strings)
      rr/response
      (rr/content-type "text/html")))
