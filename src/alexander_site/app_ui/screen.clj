(ns alexander-site.app-ui.screen
  (:require
   [garden.color :refer [rgba]]
   [garden.core :as garden]
   [garden.def :refer [defstyles]]
   [garden.stylesheet :refer [at-media]]
   [garden.units :refer [percent]]
   [hiccup.page :as hp]
   [alexander-site.app-ui.header :refer [header]]
   [alexander-site.app-ui.nav :refer [nav]]
   [alexander-site.ui-system.ui :as ui]
   [alexander-site.app-ui.colors :as colors]))

;;;; View

;;; Content

(defstyles styles-required-on-body-load
  [;; Light and dark color themes
  ])
   ; (at-media {:prefers-color-scheme :light}
   ;   [:body {:color :black
   ;           :background-color :white}])

   ; (at-media {:prefers-color-scheme :dark}
   ;   [:body {:color :#adbac7
   ;           :background-color :#282D33}]
   ;   [:a {:color (rgba 102 204 255 1)}]
   ;   [:a:active {:color (rgba 238 0 0 1)}])])

(defn head [props]
  (ui/html
   [:head
    [:meta {:charset :utf-8}]
    [:meta {:name :viewport
            :content "width=device-width, initial-scale=1"}]
    [:title (:html.doc/title props)]
    [:script {:src "https://ajax.googleapis.com/ajax/libs/webfont/1.6.26/webfont.js"}]
    [:script
     "
       WebFont.load({
         google: {
           families: [`Roboto:400,700`, `Poppins:400,700`, `Shippori Antique B1`, `Dancing Script`]
          }
       });
     "]
    [:style (garden/css styles-required-on-body-load)]
    [:link {:rel :stylesheet
            ; :href "/s/css/main.g.css"
            :href "/main.css"
            :type "text/css"}]]))

(defn body [{::keys [content] :as props}]
  (ui/html
   [:body
    [:header#header (header props)]
    [:div#main
     (nav props)
     [:section#content content]]]))

(defn html-doc
  [{:keys [app/lang] :as props}]
  (str
   (ui/html
    (hp/doctype :html5)
    [:html {:lang lang}
     (head props)
     (body props)])))

;;; Styles

(def styles
  (into
   ui/css-reset
   [;; Element defaults

    [:body {:display :grid
            :grid-template-rows [[:auto "1fr"]]
            :font-family "Poppins, Roboto, sans-serif"
            :color colors/text-black}]

    ;; Containers
    [:#main {:padding "0 15px 15px"}]
    [:#content {:height (percent 100)
                :padding "30px 150px 0"
                :font-size "20px"}]]))

;;; Strings

(def strings
  {:html.doc.title/generic "Alexander Technique Charlottesville"
   :html.doc.title/template "{1} | Alexander Technique Charlottesville"})

;;;; Renderer

(defn- add-body-content [props content-fn]
  (assoc props ::content (content-fn props)))

(defn render
  "Returns a `String` containing a complete screen in HTML.

  Uses data in `req` for processing, and includes content generated by
  `content-fn`, as well as translations of `untranslated-strings`."
  [req content-fn untranslated-strings]
  (-> req
      ui/req->props
      (ui/add-translated-strs untranslated-strings)
      (add-body-content content-fn)
      html-doc))