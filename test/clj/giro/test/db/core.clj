(ns giro.test.db.core
  (:use clojure.core
        midje.sweet
        giro.util)
  (:require [giro.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [giro.config :refer [env]]
            [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'giro.config/env
      #'giro.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(def test-giro {:name            "Test App"
                :url_apple       "apple"
                :url_googleplay  "google"
                :url_amazon      "amazon"
                :url_windows     "windows"
                :url_fallback    "fallback"
                :keen_write_key  "write"
                :keen_project_id "project"
                :keen_event      "event"
                :ga_tracking_id  "ga"
                })

(deftest test-giros
  (jdbc/with-db-transaction
    [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (let [admin_id (first (db/create-giro! t-conn test-giro))
          actual-giro (db/get-giro-by-admin-id t-conn admin_id)]

      (is (not-empty (:id actual-giro)))
      (is (= (merge test-giro admin_id)
             (select-keys-inv actual-giro [:id :created])))
      )
    )
  )
