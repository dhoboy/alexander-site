(ns alexander-site.http-error.e-406-not-acceptable-screen
  (:require
   [alexander-site.app-ui.screen :as screen]
   [alexander-site.http-error.error-screen-template :as template]))

;;;; View

(defn content [props]
  (template/content (::sorry-couldnt-find props)
                    (::error-detail-and-corrective-steps props)
                    (::error-406-not-acceptable props)))

(def strings
  {::html.doc.title.fragment "Not Acceptable"
   ::sorry-something-went-wrong "Sorry, something went wrong."
   ::error-detail-and-corrective-steps
   (str "We likely made a mistake that led to this, so we'll take a look and "
        "get it fixed as soon as we can. Please go back or use the navigation "
        "menu to continue. Sorry again!")
   ::error-406-not-acceptable "Error 406, Not Acceptable"})

(def styles
  nil)

;;;; Controller

(defn handler [req]
  {:status 406
   :headers {"Content-Type" "text/html"}
   :body (screen/render req content strings)})
