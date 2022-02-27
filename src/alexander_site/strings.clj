(ns alexander-site.strings
  "App strings, in multiple languages for runtime translation.

  NOTE: Default language strings for features/modules are defined in their
  respective namespaces to facilitate development. They are then aggregated in
  the `ui-system-data` namespace for each feature/module. These data namespaces
  must be `require`d here, and their strings must be included in the
  `default-lang-dict` to be available for rendering.

  `dict` refers to a complete set of strings for a particular language.
  `strings` refers to incomplete sets to be aggregated into a dict."
  (:require
   [alexander-site.app-ui.ui-system-data :as app-ui]
   [alexander-site.home.ui-system-data :as home]
   [alexander-site.alexander-technique.ui-system-data :as alexander-technique]
   [alexander-site.faculty.ui-system-data :as faculty]
   [alexander-site.contact.ui-system-data :as contact]
   [alexander-site.http-error.ui-system-data :as http-error]))

(def default-lang
  "The language in which all app strings are initially defined."
  :en)

(def default-lang-dict
  "Dictionary containing all default language strings in the app.

  NOTE: Default language strings for all features/modules must be included here
  to be available for rendering and translation.

  Serves as the primary reference for translation to other languages."
  (merge app-ui/strings
         home/strings
         alexander-technique/strings
         faculty/strings
         contact/strings
         http-error/strings
         ;; Feature/module strings
         ,))

(def app-dicts
  "Dictionaries used for displaying all strings in all languages in the app UI.

  NOTE: Translations must be included here to be available for rendering."
  {:tongue/fallback default-lang
   default-lang default-lang-dict

   :ja {}
   ,})

(comment
  (require '[clojure.pprint :refer [pprint]])
  (pprint app-dicts))
