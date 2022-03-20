(defproject alexander-site "220209-SNAPSHOT"
  :description "Alexander Technique School Charlottesville Homepage"
  ; :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :license {:name "None (proprietary; all rights reserved)"}
  :dependencies [[buddy/buddy-auth "3.0.323"]
                 [garden "1.3.10"]
                 [hiccup "2.0.0-alpha2"]
                 [integrant "0.8.0"]
                 [metosin/reitit-core "0.5.15"]
                 [metosin/reitit-ring "0.5.15"]
                 [org.clojure/clojure "1.10.3"]
                 [ring/ring-core "1.9.5"]
                 [ring/ring-defaults "0.3.3"]
                 [ring/ring-jetty-adapter "1.9.5"]
                 [tongue "0.4.3"]]
  :plugins [[lein-auto "0.1.3"]
            [lein-garden "0.3.0"]
            [lein-ring "0.12.5"]]
  :main alexander-site
  :target-path "target/%s"
  :repl-options {:init-ns user}

  :profiles {:dev {:dependencies [[integrant/repl "0.3.2"]]
                   :source-paths ["dev"]}
             :uberjar {:aot :all}}

  ;; Plugin config
  :garden
  {:builds [{:id "main"
             :source-paths ["src/"]
             :stylesheet alexander-site.styles/main
             :compiler {;; Generated CSS files use a ".g.css" extension
                        :output-to "resources/public/css/main.g.css"
                        :pretty-print? true}}]}

  :ring {:handler alexander-site/handler :auto-refresh? true})
