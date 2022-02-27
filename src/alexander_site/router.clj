(ns alexander-site.router
  "App router, dispatching requests for all routes from one handler function.
  Add route data from app features/modules to the app-route-data vector."
  (:require
   [alexander-site.home.ui-system-data :as home]
   [alexander-site.alexander-technique.ui-system-data :as alexander-technique]
   [alexander-site.faculty.ui-system-data :as faculty]
   [alexander-site.contact.ui-system-data :as contact]
   [alexander-site.http-error.e-404-not-found-screen :as e-404-screen]
   [alexander-site.http-error.e-405-method-not-allowed-screen :as e-405-screen]
   [alexander-site.http-error.e-406-not-acceptable-screen :as e-406-screen]
   [alexander-site.styles :as styles]
   [reitit.ring :as ring]))

(def app-route-data
  (concat [["/main.css" styles/handler]]
          home/routes
          alexander-technique/routes
          faculty/routes
          contact/routes
          ))

(comment
  (require '[clojure.pprint :refer [pprint]])
  (pprint app-route-data))

(def handler
  (ring/ring-handler
    (ring/router
      app-route-data)
    (ring/routes
      ;; Static resources mapped to "/s/" to reduce potential route conflicts
      (ring/create-resource-handler {:path "/s/"})
      (ring/create-default-handler
       ;; Error strings translated at runtime, so `constantly` can't be used
       {:not-found e-404-screen/handler
        :method-not-allowed e-405-screen/handler
        :not-acceptable e-406-screen/handler}))))
