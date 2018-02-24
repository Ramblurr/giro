(ns giro.qrgen
  (:require
    [clj.qrgen :as qr]
    )
  (:import
    [java.util Base64]
    )
  )

(defn bytes-to-base64
  "Encode bytes to a base64 string"
  [to-encode]
  (.encodeToString (Base64/getEncoder) to-encode))

(defn to-qrcode
  "Wrapper around clj.qrgen/from that creates a 300x300 qrcode with a 1 unit margin"
  [to-encode]
  (qr/from to-encode :size [300 300] :hint {qr/MARGIN 1} :charset "utf-8")
  )
(defn to-qrcode-response
  "Encodes the the passed value as a qrcode and returns an input stream"
  [to-encode]
  (qr/as-input-stream (to-qrcode to-encode))
  )

(defn to-qrcode-base64
  "Encodes the passed value as a qrcode and returns the base 64 encoded string of the png image"
  [to-encode]
  (bytes-to-base64 (qr/as-bytes (to-qrcode to-encode))))

(defn to-qrcode-data-uri
  "Encodes the passed value as a qrcode and returns the data url for the image"
  [to-encode]
  (str "data:image/png;base64," (to-qrcode-base64 to-encode)))
