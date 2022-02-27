(ns alexander-site.http-error.e-403-invalid-sec-token-screen
  (:require
   [alexander-site.app-ui.screen :as screen]
   [alexander-site.http-error.error-screen-template :as template]))

;;;; View

(defn content [props]
  (template/content (::sorry-cant props)
                    (::error-detail-and-corrective-steps props)
                    (::error-403-invalid-security-token props)))

(def strings
  {::html.doc.title.fragment "Forbidden"
   ::sorry-cant "Sorry, we can't accommodate your request."
   ::error-detail-and-corrective-steps
   (str "Something got out of sync. Please go back, refresh your browser, "
        "and try again. Or, you can use the navigation menu to continue "
        "on to something else. Sorry again!")
   ::error-403-invalid-security-token
   "Error 403, Forbidden: Invalid Security Token"})

(def styles
  nil)

;;;; Controller

(defn handler [req]
  {:status 403
   :headers {"Content-Type" "text/html"}
   :body (screen/render req content strings)})
