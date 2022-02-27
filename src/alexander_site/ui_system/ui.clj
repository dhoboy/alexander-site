(ns alexander-site.ui-system.ui
  "Functions for rendering UI, translating strings, and providing data for UI."
  (:require
   [buddy.auth :as auth]
   [clojure.string :as str]
   #_{:clj-kondo/ignore [:unresolved-var]}
   [garden.selectors :as gs
                     :refer [a after attr= before defselector ol ul]]
   [garden.stylesheet :refer [at-media]]
   [garden.units :refer [ms percent vh]]
   [hiccup2.core :as h2]
   [tongue.core :as tongue]))

;;;; CSS reset

(defselector _* "*")

(def css-reset
  ;; Acknowledgment: https://github.com/hankchizljaw/modern-css-reset
  [[:*
    (_* after)
    (_* before) {:box-sizing :border-box}]

   [:blockquote
    :body
    :dd
    :dl
    :figure
    :h1
    :h2
    :h3
    :h4
    :p {:margin 0}]

   [(ol (attr= :role :list))
    (ul (attr= :role :list))]

   [:ul {:list-style :none
         :padding-left :0px
         :margin :0px}]

   [:html:focus-within {:scroll-behavior :smooth}]

   [:body {:min-height (vh 100)
           :line-height 1.5
           :text-rendering :optimizeSpeed}]

   [(a (gs/not "[class]")) {:text-decoration-skip-ink :auto}]

   [:img
    :picture {:display :block
              :max-width (percent 100)}]

   [:button
    :input
    :select
    :textarea {:font :inherit}]

   (at-media {:prefers-reduced-motion :reduce}
    [:html:focus-within {:scroll-behavior :auto}]
    [:*
     (_* after)
     (_* before) {:animation-duration [[(ms 0.01) :!important]]
                  :animation-iteration-count [[1 :!important]]
                  :transition-duration [[(ms 0.01) :!important]]
                  :scroll-behavior [[:auto :!important]]}])])

;;;; CSS keyword transformation

(defn- trim-starting-colon-and-period [kw-str]
  (if (= (second kw-str) \.)
    (subs kw-str 2)
    (subs kw-str 1)))

(defn css-class-str
  "Returns a `String` containing a CSS class for `class-sel-kw`.

  Throws an AssertionError if `class-sel-kw` is neither qualified nor a class
  selector. Unqualified keyword names must begin with a period."
  [class-sel-kw]
  {:pre [(or (qualified-keyword? class-sel-kw)
             (= \. (-> class-sel-kw str second)))]}
  (-> class-sel-kw
      str
      trim-starting-colon-and-period
      (str/replace "/" "__")
      (str/replace "." "_")))

(defn garden-sel-kw
  "Returns a Garden CSS selector keyword for `qualified-kw`.

  Throws an AssertionError if `qualified-kw` is unqualified."
  [qualified-kw]
  {:pre [(qualified-keyword? qualified-kw)]}
  (->> qualified-kw
       css-class-str
       (str ".")
       keyword))

(defn transform-qualified-sel-kws-for-garden-css-compilation
  "Returns styles with qualified selector keywords transformed for compilation."
  [styles]
  (->> styles
       (map #(if-let [kw (when (vector? %) (first %))]
               (if (qualified-keyword? kw)
                 (assoc % 0 (garden-sel-kw kw))
                 %)
               %))
       vec))

;;;; HTML rendering

(defn html
  "Returns an object wrapping a `String` of HTML rendered from `hiccup` data.
  These objects may be rendered, included in other hiccup data, and passed
  again to `html` without being re-rendered. Use `clojure.core/str` to produce
  an HTML `String` for use in browsers.

  An option map (literal or variable) may be specified as the first argument.
  It accepts two keys that control how the HTML is outputted:

  `:mode`
  : One of `:html`, `:xhtml`, `:xml` or `:sgml` (defaults to `:html` instead of
    the hiccup default of `:xhtml`). Controls how tags are rendered.

  `:escape-strings?`
  : True if strings should be escaped (defaults to true). To insert a string
    without it being escaped, use the `dangerously-unescapable-string`
    function."
  ([options & hiccup]
   (if (map? options)
     (let [mode (:mode options :html)
           escape-strings? (:escape-strings? options true)]
       ;; Since hiccup2.core/html accepts only map literals (no variables)
       ;; for options, we need a separate call for every options combination
       (case mode
         :html (if escape-strings?
                 (h2/html {:mode :html, :escape-strings? true} hiccup)
                 (h2/html {:mode :html, :escape-strings? false} hiccup))
         :xhtml (if escape-strings?
                  (h2/html {:mode :xhtml, :escape-strings? true} hiccup)
                  (h2/html {:mode :xhtml, :escape-strings? false} hiccup))
         :xml (if escape-strings?
                (h2/html {:mode :xml, :escape-strings? true} hiccup)
                (h2/html {:mode :xml, :escape-strings? false} hiccup))
         :sgml (if escape-strings?
                 (h2/html {:mode :sgml, :escape-strings? true} hiccup)
                 (h2/html {:mode :sgml, :escape-strings? false} hiccup))))
     (h2/html {:mode :html, :escape-strings? true} options hiccup))))

(def ^{:arglists '([& strings])} dangerously-unescapable-string
  "Returns an object wrapping `strings` which will not be escaped by `html`.

  CAUTION: use only with trusted data, never with user input or data from third
  parties, as this can expose users to Cross-Site Scripting (XSS) attacks."
  h2/raw)

(defn cs
  "Returns a `String` containing CSS `classes` separated by spaces.

  `classes` may be a mix of CSS class `String`s, qualified keywords, or Garden
  class selector keywords. Throws an AssertionError on any keyword that is
  neither qualified nor a class selector. Unqualified keyword names must begin
  with a period."
  [& classes]
  (->> classes
       (map #(if (keyword? %) (css-class-str %) %))
       (str/join " ")))

(defn class-m
  "Returns a single-entry map: `{:class \"class1 class2 class3 ...\"}`.

  `classes` may be a mix of CSS class `String`s, qualified keywords, or Garden
  class selector keywords. Throws an AssertionError on any keyword that is
  neither qualified nor a class selector. Unqualified keyword names must begin
  with a period."
  [& classes]
  {:class (apply cs classes)})

;;;; String translation

;; HACK: see docstring
(defn def-tr-fns!
  "Globally defines `tr` and `tr-lang` functions using `app-dicts`.

  HACK: works around circular dependencies from strings being defined in their
  respective screen/view ns's, then merged in the `strings` ns, then accessed
  via translation functions in this ns, which the screen/view ns's require.

  Must be called on app start."
  [app-dicts]
  #_{:clj-kondo/ignore [:inline-def]}
  (def ^{:arglists '([lang key & args])} tr
    "Returns the string best matching `lang`, `key`, and optional `args`.

    If no match is found in the given language, a matching string from a related
    language or from the fallback language may be returned. If no match for the
    key is found in these other languages, a \"Missing key\" error string is
    returned."
    (tongue/build-translate app-dicts))
  #_{:clj-kondo/ignore [:inline-def]}
  (defn tr-lang
    "Returns the language in `dicts` that best matches `lang`."
    ([lang] (tr-lang lang app-dicts))
    ([lang dicts]
     (if (some? lang)
       (or
         ;; NOTE: calls private tongue function to reuse its cache. This may
         ;; fail upon upgrading tongue versions if the implementation changes.
         (loop [tags (#'tongue/tags lang)]
           (when-some [tag (first tags)]
             (if (contains? dicts tag)
               tag
               (recur (next tags)))))
         (:tongue/fallback dicts))
       (:tongue/fallback dicts)))))

(defn- trs-with-html-doc-title [lang trs]
  (if (some? (:html.doc/title trs))
    trs
    (assoc trs :html.doc/title (or (tr lang :html.doc.title/generic)
                                   "Missing title"))))

(defn view-string-trs
  "Returns translations of `view-strings` that best match `lang`."
  [lang view-strings]
  (->> view-strings
       (reduce (fn [trs vs-entry]
                 (let [k (key vs-entry)
                       s (tr lang k)]
                   (if (= (name k) "html.doc.title.fragment")
                     (assoc trs :html.doc/title
                                (tr lang :html.doc.title/template s))
                     (assoc trs k s))))
               {})
       (trs-with-html-doc-title lang)))

;;;; Props construction

(defn first-accept-language
  "Returns the first language tag in the Accept-Language header of `req`."
  [req]
  (when-let [accept-language (get-in req [:headers "accept-language"])]
    (if-let [idx (str/index-of accept-language ",")]
      (subs accept-language 0 idx)
      accept-language)))

(defn req->props
  "Returns a map containing props for rendering ui, derived from `req`."
  [{:keys [uri]
    #_{:clj-kondo/ignore [:shadowed-var]}
    {:keys [identity]} :session
    {:strs [_accept-language]} :headers
    :as req}]
  (let [req-lang (first-accept-language req)
        app-lang (tr-lang req-lang)]
    {:acct/authenticated? (auth/authenticated? req)
     :acct/handle (:account/handle identity)
     :acct/id (:account/id identity)
     :app/lang app-lang
     :req/lang req-lang
     :req/uri uri}))

(defn add-translated-strs [{:app/keys [lang] :as props} view-strings]
  (merge props (view-string-trs lang view-strings)))
