(ns giro.routes.link
  (:require
    [compojure.core :refer [defroutes GET POST]]
    [ring.util.http-response :as response]
    [clojure.java.io :as io]
    [struct.core :as st]
    [try-let :refer [try-let]]
    [giro.config :refer [env]]
    [giro.layout :as layout]
    [giro.db.core :as db]
    [giro.qrgen :as qrgen]
    [giro.user-agent :as ua]
    ))

;(def giro-url (env :giro-url))
(def giro-url "http://localhost:3000")

; --- helpers
(defn public-uri [g]
  (str giro-url (:id g)))

(defn giro-with-qrcode
  "Given a giro, returns the giro qr code as a data url  (base64 encoded version of the png bytes)"
  [g]
  (assoc g :qrcode (qrgen/to-qrcode-data-uri (public-uri g))))

(defn qrcode-for-giro
  "Given a giro, returns an input stream of the qr code png"
  [g]
  (qrgen/to-qrcode-response (public-uri g)))

(def giro-schema
  [[:name st/required st/string]
   [:url_fallback st/required st/string]])

(defn validate-message [params]
  (first
    (st/validate params giro-schema)))

(defn extract-giro [params]
  (select-keys params [:name, :url_apple, :url_googleplay, :url_amazon, :url_windows, :url_fallback, :keen_write_key,
                       :keen_project_id, :keen_event, :ga_tracking_id, :admin_id, :id]))

(defn platform->giro-key [platform]
  ({
    :amazon       :url_amazon
    :ios          :url_apple
    :macosx       :url_apple
    :windowsphone :url_windows
    :win          :url_windows
    :android      :url_googleplay
    :unknown      :url_fallback
    nil           :url_fallback
    } platform)
  )

(defn platform->giro-url [platform giro]
  (if-some [url (giro (platform->giro-key platform))]
    url
    (:url_fallback giro)))

(defn giro-url-for-ua [giro user-agent]
  (platform->giro-url (ua/detect-platform user-agent) giro))

; --- route handlers
(defn response-404 []
  (layout/error-page {:status 404 :title "Not found"}))

(defn giro-edit-page
  "Renders the giro creation/edit page"
  [{:keys [flash admin_id]}]
  (layout/render
    "link.html"
    (assoc
      (select-keys flash [:name :url_fallback :errors :toast])
      :giro-url
      giro-url
      :giro
      (when (not (nil? admin_id))
        (giro-with-qrcode
          (db/get-giro-by-admin-id {:admin_id admin_id})))
      ))
  )
(defn giro-as-png
  "Returns the giro's qrcode as an image"
  [{:keys [id]}]
  (-> (qrcode-for-giro (db/get-giro {:id id}))
      response/ok
      (response/content-type "image/png"))
  )

(defn save-giro!
  "Save/update a giro"
  [{:keys [params]}]
  (if-let [errors (validate-message params)]

    (-> (response/found "/")
        (assoc :flash (assoc params :errors errors)))
    (do
      (let [save-fn! (if (empty? (:admin_id params)) db/create-giro! db/update-giro!)
            admin_id (:admin_id (first (save-fn! (extract-giro params))))
            ]
        (response/found (str "/admin/" admin_id))
        )
      )))

(defn execute-giro
  "Redirects the user to the appropriate giro destination"
  [request]
  (try-let [id {:id (get-in request [:params :id])}
            user-agent (get-in request [:headers "user-agent"])
            giro (db/get-giro id)]
           (if-not (nil? giro)
             (response/found (giro-url-for-ua giro user-agent))
             (response-404))
           (catch Exception e
             (response-404))))


(defroutes link-routes
           (GET "/" request (giro-edit-page request))
           (GET "/admin/:admin_id" [admin_id] (giro-edit-page {:admin_id admin_id}))
           (GET "/:id/png" [id] (giro-as-png {:id id}))
           (GET "/:id" request (execute-giro request))
           (GET "/admin/new-link" request (giro-edit-page request))

           (POST "/" request (save-giro! request))
           (POST "/admin/:admin_id" request (save-giro! request))
           )

