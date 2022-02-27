(ns alexander-site.home.home-screen
  "App home screen view and controller."
  (:require
   [alexander-site.app-ui.screen :as screen]
   [alexander-site.ui-system.ui :as ui]
   [garden.def :refer [defstyles]]
   [ring.util.response :as rr]))

;;;; View

(defn content [props]
  (ui/html
   [:div.home-screen
     [:h3 "About"]
     [:p (::blurb props)]
     [:div.details
       [:h3 "Training Course Details"]
       [:ul
         [:li (::details props)]
         [:li (::classes props)]
         [:li (::time props)]
         [:li (::months props)]
         [:li (::tuition props)]]]]))

(def strings
  {::html.doc.title.fragment "Home"
   ::blurb (str "The Alexander Technique Training Center in Charlottesville "
                "(ATTC-C) is dedicated to providing a thorough education in the "
                "principles of the Alexander Technique as defined by F. M. "
                "Alexander and to training teachers to the highest training course "
                "standards as established by AmSAT. The faculty of ATTC-C holds "
                "kindness, sincerity, self-respect, humility, and commitment as "
                "core values in their teaching approach. They aspire to foster "
                "and develop the best Alexander Technique teacher in each trainee. "
                "By requiring a thorough understanding of the principles of the "
                "technique, the writings of F. M. Alexander, hands on procedures, "
                "and a strong personal dedication to self-awareness and good use, "
                "ATTC-C faculty seek to establish a firm foundation upon which "
                "each trainee can develop as a teacher. Within this framework, "
                "this school encourages trainees to explore their unique interests "
                "and talents in relationship to becoming a teacher and bringing "
                "this work to specific populations.")
   ::details "1,600 hour AmSAT approved training"
   ::classes "Classes meet Monday - Thursday"
   ::time "9:15 am - 12:45 pm"
   ::months "September - May"
   ::tuition "$525.00 monthly"})


(defstyles styles
  [:.home-screen
   [:h3 {:padding-bottom "5px"}]
   [:.details {:padding-top "40px"}
    [:ul {:list-style "initial"
           :padding-left "22px"}]]])

;;;; Controller

(defn handler [req]
  (-> req
      (screen/render content strings)
      rr/response
      (rr/content-type "text/html")))
