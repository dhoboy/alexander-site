(ns alexander-site
  "System entry point and manager."
  (:gen-class)
  (:require
   [integrant.core :as ig]
   [alexander-site.http-error.e-403-invalid-sec-token-screen :as e-403-ist-screen]
   [alexander-site.router :as router]
   [alexander-site.strings :as strs]
   [alexander-site.ui-system.ui :as ui]
   [ring.adapter.jetty :as jetty]
   [environ.core :refer [env]]
   [ring.middleware.defaults :refer [wrap-defaults] :as rmd]))

;;;; Top-level app handler

(def handler
  (-> router/handler
      (wrap-defaults (-> rmd/site-defaults
                         (assoc-in [:security :anti-forgery]
                                   {:error-handler e-403-ist-screen/handler})
                         (assoc-in [:session :cookie-attrs :max-age]
                                   (* 60 60 24 365))
                         (assoc-in [:session :cookie-name] "session")))))

(comment
  (handler {:uri "/" :request-method :get})
  (handler {:uri "/MISMATCH" :request-method :get}))

;;;; System (Integrant)

(def system-config
  {:prod.web.adapter/jetty {:port (Integer. (or (env :port) 80))
                            :join? false
                            :handler (ig/ref :prod.web/handler)}
   :prod.web/handler {}})

(defmethod ig/init-key :prod.web.adapter/jetty [_ {:keys [port join? _handler]}]
  (jetty/run-jetty handler {:port port :join? join?}))

(defmethod ig/halt-key! :prod.web.adapter/jetty [_ server]
  (.stop server))

(defmethod ig/init-key :prod.web/handler [_ _]
  handler)

;;;; Translation fns initialization

(ui/def-tr-fns! strs/app-dicts)

;;;; App entry point

(defn -main []
  (ig/init system-config))
