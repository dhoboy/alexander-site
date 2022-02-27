(ns alexander-site.faculty.faculty-screen
  "Faculty screen view and controller."
  (:require
   [alexander-site.app-ui.screen :as screen]
   [alexander-site.ui-system.ui :as ui]
   [garden.def :refer [defstyles]]
   [ring.util.response :as rr]))

;;;; View

;; TODO: Responsive styles, adding photos, favicon

(defn content [props]
  (ui/html
   [:div.faculty-screen
    [:h3 (::garnett props)]
    [:div.garnett-photo]
    [:h3 (::lydia props)]]))

(def strings
  {::html.doc.title.fragment "Faculty"
   ::garnett "Garnett Mellen"
   ::garnet-blurb (str "Garnett is an ACE Personal Trainer and teaches Yoga, "
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

(defstyles styles
  [:.garnett-photo {:background-image "url(/s/garnett.jpeg)"}])

;;;; Controller

(defn handler [req]
  (-> req
      (screen/render content strings)
      rr/response
      (rr/content-type "text/html")))
