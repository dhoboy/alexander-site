(ns alexander-site.http-error.ui-system-data
  (:require
   [alexander-site.http-error.e-400-bad-request-screen :as e-400-screen]
   [alexander-site.http-error.e-403-invalid-sec-token-screen :as e-403-ist-screen]
   [alexander-site.http-error.e-404-not-found-screen :as e-404-screen]
   [alexander-site.http-error.e-405-method-not-allowed-screen :as e-405-screen]
   [alexander-site.http-error.e-406-not-acceptable-screen :as e-406-screen]
   [alexander-site.http-error.error-screen-template :as e-screen-template]))

(def routes
  nil)

(def strings
  (merge e-screen-template/strings
         e-400-screen/strings
         e-403-ist-screen/strings
         e-404-screen/strings
         e-405-screen/strings
         e-406-screen/strings))

(def styles
  (concat e-screen-template/styles
          e-400-screen/styles
          e-403-ist-screen/styles
          e-404-screen/styles
          e-405-screen/styles
          e-406-screen/styles))
