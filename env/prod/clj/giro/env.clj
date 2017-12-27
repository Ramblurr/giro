(ns giro.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[giro started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[giro has shut down successfully]=-"))
   :middleware identity})
