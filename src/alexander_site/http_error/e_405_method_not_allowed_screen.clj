(ns alexander-site.http-error.e-405-method-not-allowed-screen
  (:require
   [alexander-site.app-ui.screen :as screen]
   [alexander-site.http-error.error-screen-template :as template]))

;;;; View

(defn content [props]
  (template/content (::sorry-something-went-wrong props)
                    (::error-detail-and-corrective-steps props)
                    (::error-405-method-not-allowed props)))

(def strings
  {::html.doc.title.fragment "Method Not Allowed"
   ::sorry-something-went-wrong "Sorry, something went wrong."
   ::error-detail-and-corrective-steps
   (str "We likely made a mistake that led to this, so we'll take a look and "
        "get it fixed as soon as we can. Please go back or use the navigation "
        "menu to continue. Sorry again!")
   ::error-405-method-not-allowed "Error 405, Method Not Allowed"})

(def styles
  nil)

;;;; Controller

(defn handler [req]
  {:status 405
   :headers {"Content-Type" "text/html"}
   :body (screen/render req content strings)})
