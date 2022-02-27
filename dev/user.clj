(ns user
  "Commonly used symbols for use in the REPL during development."
  #_{:clj-kondo/ignore [:unused-referred-var :unused-namespace]}
  (:require
   [clojure.java.io :as io]
   [clojure.pprint :refer (pprint)]
   [clojure.repl :refer [apropos demunge dir dir-fn doc find-doc pst root-cause
                         set-break-handler! source source-fn stack-element-str
                         thread-stopper]]
   [clojure.string :as str]
   [clojure.test :as test]
   [integrant.repl :refer [clear go halt prep init reset reset-all
                           resume suspend] :as ig.repl]
   [integrant.repl.state :as ig.repl.state]
   alexander-site))

(ig.repl/set-prep! (constantly alexander-site/system-config))

(comment
  (go)
  (identity ig.repl.state/system)
  (reset)
  (halt))
