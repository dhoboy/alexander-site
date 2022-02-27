(ns alexander-site.http-error.e-404-not-found-screen
  (:require
   [alexander-site.app-ui.screen :as screen]
   [alexander-site.http-error.error-screen-template :as template]))

;;;; View

(defn content [props]
  (template/content (::sorry-couldnt-find props)
                    (::error-detail-and-corrective-steps props)
                    (::error-404-not-found props)))

(def strings
  {::html.doc.title.fragment "Not Found"
   ::sorry-couldnt-find "Sorry, we couldn't find what you were looking for."
   ::error-detail-and-corrective-steps
   (str "What was here might have been removed, or there might be a mistake in "
        "the link. Please check the link, or go back or use the navigation "
        "menu to continue. Sorry again!")
   ::error-404-not-found "Error 404, Not Found"})

(def styles
  nil)

;;;; Controller

(defn handler [req]
  {:status 404
   :headers {"Content-Type" "text/html"}
   :body (screen/render req content strings)})
