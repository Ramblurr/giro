(ns giro.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [giro.layout :refer [error-page]]
            [giro.routes.home :refer [home-routes]]
            [giro.routes.link :refer [link-routes]]
            [compojure.route :as route]
            [giro.env :refer [defaults]]
            [mount.core :as mount]
            [giro.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    (->
        #'link-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
