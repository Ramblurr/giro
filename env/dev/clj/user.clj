(ns user
  (:require [luminus-migrations.core :as migrations]
            [giro.config :refer [env]]
            [mount.core :as mount]
            giro.core))

(defn start []
  (mount/start-without #'giro.core/repl-server))

(defn stop []
  (mount/stop-except #'giro.core/repl-server))

(defn restart []
  (stop)
  (start))

(defn migrate []
  (migrations/migrate ["migrate"] (select-keys env [:database-url])))

(defn rollback []
  (migrations/migrate ["rollback"] (select-keys env [:database-url])))

(defn create-migration [name]
  (migrations/create name (select-keys env [:database-url])))


