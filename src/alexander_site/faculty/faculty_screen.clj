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
   ::garnett-blurb (str "Garnett is an ACE Personal Trainer and teaches Yoga, "
                       "Pilates, Qigong and American Waltz. People who strive "
                       "for vigor, strength and robust health find her training "
                       "sessions both enlivening, while they quiet the nervous "
                       "system. For those who have elevated blood sugar and who "
                       "want to avoid developing Type II Diabetes, she draws upon "
                       "her CDC Lifestyle Coach certification to safely guide "
                       "weight loss. In her sessions Garnett emphasizes posture "
                       "and balance drawing upon her extensive trainings in the "
                       "Alexander Technique.")
   ::lydia "Lydia von Briesen"
   ::lydia-blurb "Lydia teaches Alexander and Yoga..."})

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
