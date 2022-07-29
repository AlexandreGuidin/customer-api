(ns api.logic.authorization
  (:require [buddy.sign.jwt :as jwt]))

(defn decode-token
  [token]
  (jwt/unsign token "secret")
  )

(defn token-valid?
  [token]
  (not (nil? (decode-token token))))

