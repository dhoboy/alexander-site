(ns alexander-site.alexander-technique.alexander-screen
  "Alexander Technique screen view and controller."
  (:require
   [alexander-site.app-ui.screen :as screen]
   [alexander-site.ui-system.ui :as ui]
   [garden.core :refer [css]]
   [garden.def :refer [defstyles]]
   [ring.util.response :as rr]))

;;;; View

;; Screens render entire pages

(defn content [props]
  "Alexander Technique Screen content"
  (ui/html
    (let [link "https://www.amsatonline.org"]
     [:div.alexander-technique-screen
       [:div.question
         [:h3 (::what-is-it props)]
         [:p (::answer-what props)]]
       [:div.question
         [:h3 (::why-study-it props)]
         [:p (::answer-why props)]
         [:p (::answer-why-long props)]
         [:p
          [:span (::link-sentence-pt1 props)]
          [:a {:href link :target "_blank"}
            (::link-text props)]
          [:span (::link-sentence-pt2 props)]]]])))


(def strings
  {::html.doc.title.fragment "Alexander Technique"
   ::what-is-it "What is the Alexander Technique?"
   ::answer-what (str "The Alexander Technique is an educational method used "
                      "worldwide for over 100 years. By teaching how to change faulty "
                      "postural habits, it enables improved mobility, posture, "
                      "performance and alertness along with relief of chronic stiffness, "
                      "tension and stress.")
   ::why-study-it "Why do people study the Alexander Technique?"
   ::answer-why (str "People study the Technique for a variety of reasons. The most "
                     "common is to relieve pain through learning better "
                     "coordination of the musculoskeletal system.")
   ::answer-why-long (str "Another reason people take lessons in the Alexander Technique "
                          "is to enhance performance. Athletes, singers, dancers, and "
                          "musicians use the Technique to improve breathing, vocal "
                          "production, and speed and accuracy of movement. The most "
                          "far-reaching reason people study the Technique is to achieve "
                          "greater conscious control of their reactions. Most of us have "
                          "many habitual patterns of tension, learned both consciously "
                          "and unconsciously. These patterns can be unlearned, enabling "
                          "the possibility of new choices in posture, movement and reaction. "
                          "During lessons you’ll develop awareness of habits that "
                          "interfere with your natural coordination.  You’ll learn how "
                          "to undo these patterns and develop the ability to consciously "
                          "redirect your whole self into an optimal state of being and "
                          "functioning. Through direct experience you’ll learn how to "
                          "go about your daily activities with increasingly greater ease "
                          "and less effort.")
   ::link-sentence-pt1 "Visit "
   ::link-text "AmSat"
   ::link-sentence-pt2 " for more information on the Alexander Technique!"})

(def styles
  [[:div.alexander-technique-screen
   [:div.question {:padding-bottom "30px"}
    [:h3 {:padding-bottom "5px"}]
    [:p {:padding-bottom "15px"}]]]])

;;;; Controller

(defn handler [req]
  "Exported Alexander Technique Screen"
  (-> req
      (screen/render content strings)
      rr/response
      (rr/content-type "text/html")))
