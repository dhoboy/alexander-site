(ns alexander-site.faculty.faculty-screen
  "Faculty screen view and controller."
  (:require
   [alexander-site.app-ui.screen :as screen]
   [alexander-site.ui-system.ui :as ui]
   [garden.def :refer [defstyles]]
   [ring.util.response :as rr]))

;;;; View

;; Screens render entire pages

;; TODO: Responsive styles, adding photos, favicon
;; TODO: Handle the dark/light color theme stuff built in

(defn content [props]
  "Faculty Screen content"
  (ui/html
   [:div.faculty-screen
     [:h3 (::garnett props)]
     [:div.entry
       [:div.bio-photo.garnett]
       [:p (::garnett-blurb props)]]
     [:h3 (::lydia props)]
     [:div.entry
       [:div.bio-photo.lydia]
       [:p (::lydia-blurb props)]]]))

(def strings
  {::html.doc.title.fragment "Faculty"
   ::garnett "Garnett Mellen"
   ::garnett-blurb (str "Garnett, in addition to being an Alexander Technique "
                        "Teacher, is an ACE Personal Trainer who teaches Yoga "
                        "and Pilates. People who strive for vigor, strength "
                        "and robust health find her sessions enlivening, while "
                        "they also quiet the nervous system. She graduated from "
                        "the Alexander Technique Training Center in 2012. "
                        "She loves plants, being outside, dancing and funky "
                        "earrings and, of course, the Alexander Technique. ")
   ::lydia "Lydia von Briesen"
   ::lydia-blurb (str "Lydia has been teaching the Alexander Technique for 15 "
                      "years. She loves to use the technique in everyday life "
                      "situations like parenting and manual labor. One of her "
                      "favorite practices is teaching the technique as it applies "
                      "to yoga. Her living philosophy is based on the principles "
                      "that there is basic goodness at the core of every human, "
                      "human bodies are perfect and sacred, and the universe is "
                      "always on our side." )})

(def styles
  [[:div.faculty-screen
    [:h3 {:padding-bottom "5px"}]
    [:.entry {:display "grid"
              :grid-template-columns "300px 1fr"
              :padding-bottom "75px"}
      [:.bio-photo
        {:height "400px"
         :border-radius "4px"
         :background-size "cover"
         :background-position "50%"}]
      [:.garnett {:background-image "url(/s/garnett.jpeg)"}]
      [:.lydia {:background-image "url(/s/lydia.jpeg)"}]
      [:p {:padding-left "25px"}]]]
   ["div.faculty-screen > div:last-child" {:padding-bottom "0px"}]])

;;;; Controller

(defn handler [req]
  "Exported Faculty screen"
  (-> req
      (screen/render content strings)
      rr/response
      (rr/content-type "text/html")))
