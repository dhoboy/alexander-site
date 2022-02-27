(ns alexander-site.contact.ui-system-data
  (:require
    [alexander-site.contact.contact-screen :as contact-screen]))

(def routes
  [["/contact" contact-screen/handler]])

(def strings
  (merge contact-screen/strings))

(def styles
  (concat contact-screen/styles))
