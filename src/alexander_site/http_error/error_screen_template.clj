(ns alexander-site.http-error.error-screen-template
  (:require
   [garden.units :refer [em]]
   [alexander-site.ui-system.ui :as ui :refer [class-m]]))

(defn content [heading detail http-error]
  (ui/html
   [:h2 (class-m ::heading) heading]
   [:p (class-m ::detail) detail]
   [:p (class-m ::http-error) http-error]))

(def strings
  nil)

(def styles
  [[::heading {:padding-bottom (em 1)}]
   [::detail {:padding-bottom (em 1)}]
   [::http-error {:font-style :italic}]])
