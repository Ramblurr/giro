(defproject giro "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :repositories [["jitpack" "https://jitpack.io"]]
  :dependencies [[clj-time "0.14.2"]
                 [compojure "1.6.0"]
                 [conman "0.7.4"]
                 [cprop "0.1.11"]
                 [funcool/struct "1.1.0"]
                 [luminus-immutant "0.2.4"]
                 [luminus-migrations "0.4.3"]
                 [luminus-nrepl "0.1.4"]
                 [luminus/ring-ttl-session "0.3.2"]
                 [markdown-clj "1.0.1"]
                 [metosin/muuntaja "0.4.1"]
                 [metosin/ring-http-response "0.9.0"]
                 [mount "0.1.11"]
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.postgresql/postgresql "42.1.4"]
                 [org.webjars.bower/tether "1.4.0"]
                 [org.webjars/font-awesome "4.7.0"]
                 [org.webjars/jquery "3.2.1"]
                 [ring-webjars "0.2.0"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-defaults "0.3.1"]
                 [selmer "1.11.3"]
                 ; below not from luminus
                 [org.webjars.bower/bulma "0.6.1"]
                 [try-let "1.1.0"]
                 ^{:voom {:repo "https://github.com/Ramblurr/clj.qrgen" :branch "master"}}
                 [clj.qrgen "0.4.0-20180102_145805-gdcf4606"]
                 ]

  :min-lein-version "2.0.0"

  :jvm-opts ["-server" "-Dconf=.lein-env"]
  :source-paths ["src/clj"]
  :test-paths ["test/clj"]
  :resource-paths ["resources"]
  :target-path "target/%s/"
  :main ^:skip-aot giro.core
  :migratus {:store :database :db ~(get (System/getenv) "DATABASE_URL")}

  :plugins [[lein-cprop "1.0.3"]
            [migratus-lein "0.5.3"]
            [lein-immutant "2.1.0"]
            [lein-sassc "0.10.4"]
            ;[deraen/lein-sass4clj "0.3.1"]
            [lein-auto "0.1.2"]
            [yogthos/lein-watch "0.0.4"]]
  ;:sass {
  ;       :target-path  "resources/public/css/screen.css"
  ;       :source-paths ["resources/scss/screen.scss"]
  ;       :output-style :nested
  ;       }
   :sassc
  [{:src         "resources/scss/screen.scss"
    :output-to   "resources/public/css/screen.css"
    :style       "nested"
    :import-path "resources/scss"}]
  :watch
  {:rate     150
   :color    :blue
   :watchers {:sassc {:watch-dirs    ["resources/scss"]
                      :file-patterns [#"\.(scss|sass)$"]
                      :tasks         ["sassc once"]}}}

  :auto
  {"sassc" {:file-pattern #"\.(scss|sass)$" :paths ["resources/scss"]}}

  :hooks [leiningen.sassc]

  :profiles
  {:uberjar       {:omit-source    true
                   :aot            :all
                   :uberjar-name   "giro.jar"
                   :source-paths   ["env/prod/clj"]
                   :resource-paths ["env/prod/resources"]}

   :dev           [:project/dev :profiles/dev]
   :test          [:project/dev :project/test :profiles/test]

   :project/dev   {:dependencies  [[prone "1.1.4"]
                                   [ring/ring-mock "0.3.2"]
                                   [ring/ring-devel "1.6.3"]
                                   [midje "1.9.1"]
                                   [pjstadig/humane-test-output "0.8.3"]]
                   :plugins       [[lein-midje "3.2.1"]
                                   [com.jakemccrary/lein-test-refresh "0.19.0"]]

                   :source-paths  ["env/dev/clj"]
                  :resource-paths ["env/dev/resources"]
                   :repl-options  {:init-ns user}
                   :injections    [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]}
   :project/test  {:resource-paths ["env/test/resources"]}
   :profiles/dev  {}
   :profiles/test {}})
