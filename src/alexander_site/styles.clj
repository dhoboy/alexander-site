(ns alexander-site.styles
  "App style data for compiling stylesheets as CSS.

  NOTE: Styles for features/modules are defined in their respective namespaces
  to facilitate development, then aggregated in the `ui-system-data` namespace
  for each feature/module. These data namespaces must be `require`d here, and
  their styles must be included in a `defstyles` definition to be available for
  compiling into stylesheets.

  The style data for the `main` stylesheet is defined below. The accompanying
  handler dynamically compiles this `main` stylesheet for linking by app pages.
  This is particularly helpful during development, allowing fast feedback on
  style changes. It should also serve well in lower-traffic production
  environments where performance is not critical; however, if profiling shows
  that using a compiled static file provides needed performance gains, use the
  `lein-garden` plugin. See the `:garden` config in `project.clj`.

  To define data for new stylesheets, use `defstyles` below and either add a
  handler, or setup building a static file in the `:garden` config in
  `project.clj`."
  (:require
   [garden.core :as garden]
   [garden.def :refer [defstyles]]
   [alexander-site.app-ui.ui-system-data :as app-ui]
   [alexander-site.home.ui-system-data :as home]
   [alexander-site.alexander-technique.ui-system-data :as alexander-technique]
   [alexander-site.faculty.ui-system-data :as faculty]
   [alexander-site.contact.ui-system-data :as contact]
   [alexander-site.http-error.ui-system-data :as http-error]
   [alexander-site.ui-system.ui :as ui]
   [ring.util.response :as rr]))

(defstyles main
  (ui/transform-qualified-sel-kws-for-garden-css-compilation
    (concat app-ui/styles
            home/styles
            alexander-technique/styles
            faculty/styles
            contact/styles
            http-error/styles
            ;; Feature/module styles
            ,)))

(comment
  (require '[clojure.pprint :refer [pprint]])
  (pprint main))

(defn handler [_req]
  (-> main
      garden/css
      rr/response
      (rr/content-type "text/css")))
