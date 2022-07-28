(ns api.logic.authentication
  (:require [api.models.jwt :as jwtm]
            [clojure.data.codec.base64 :as b64]
            [clojure.string :as str]
            [buddy.sign.jwt :as jwt]))

(def expected-users {"admin" "admin-pwd", "user" "user-pwd"})

(defn throw-unauthorized
  ([message] (throw (ex-info message {:type :authorization-error})))
  ([message cause] (throw (ex-info message {:type :authorization-error} cause)))
  )

(defn decode
  [base64]
  (try (String. (b64/decode base64))
       (catch Exception e (throw-unauthorized "Failed to decoded header" e))))

(defn decode-auth-header
  [auth-header]
  (let [decoded (-> auth-header
                    (.getBytes)
                    (decode)
                    (str/trim))]
    (if (str/includes? decoded ":")
      decoded
      (throw-unauthorized "Given token was not a basic auth header"))))

(defn extract-user-and-pwd
  [auth-header]
  (str/split auth-header #":"))

(defn is-authenticated?
  [[user pwd] user-map]
  (= pwd (get user-map user)))


(defn generate-jwt-token
  []
  (jwt/sign {} "secret"))

(defn basic-auth [basic-header]
  (as-> basic-header bind
        (decode-auth-header basic-header)
        (extract-user-and-pwd bind)
        (if (is-authenticated? bind expected-users)
          (generate-jwt-token)
          (throw-unauthorized "Given user was not authorized"))
        (jwtm/new-token bind 900)
        ))
