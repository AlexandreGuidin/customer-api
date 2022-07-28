(ns api.logic.authorization
  (:require [api.models.jwt :as jwtm]
            [clojure.data.codec.base64 :as b64]
            [clojure.string :as str]
            [buddy.sign.jwt :as jwt]))

(defn decode-token
  [token]
  (jwt/unsign token "secret")
  )

(defn token-valid?
  [token]
  (not (nil? (decode-token token))))

