(ns api.logic.authentication
  (:require [api.models.jwt :as jwtm]
            [clojure.data.codec.base64 :as b64]
            [clojure.string :as str]))


(def expected-users {"admin" "admin-pwd", "user" "user-pwd"})


(defn- decode-auth-header
  [auth-header]
  (-> auth-header
      (.getBytes)
      (b64/decode)
      (String.)))

(defn- extract-user-and-pwd
  [auth-header]
  (str/split auth-header #":"))

(defn- is-authenticated?
  [[user pwd] user-map]
  (->> user
      (get user-map ,,,)
       (println pwd )
       (= pwd ,,,)))

(defn- generate-jwt-token
  []
  "a123")

(defn basic-auth [basic-header]
  (as-> basic-header bind
      (decode-auth-header basic-header)
      (extract-user-and-pwd bind)
      (if (is-authenticated? bind expected-users) (generate-jwt-token))
      (jwtm/new-token bind 900)
      ))
