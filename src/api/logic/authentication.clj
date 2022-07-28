(ns api.logic.authentication
  (:require [api.models.jwt :as jwtm]
            [clojure.data.codec.base64 :as b64]
            [clojure.string :as str]
            [buddy.sign.jwt :as jwt]))

(def expected-users {"admin" "admin-pwd", "user" "user-pwd"})

(defn decode
  [base64]
  (String. (b64/decode base64)))

(defn decode-auth-header
  [auth-header]
  (let [decoded (-> auth-header
                    (.getBytes)
                    (decode)
                    (str/trim))]
    (if (str/includes? decoded ":")
      decoded
      (throw (RuntimeException. "Given token was not a basic auth header")))))

(defn extract-user-and-pwd
  [auth-header]
  (str/split auth-header #":"))

;curl -X POST -H "Authorization: Basic YWRtaW46YWRtaW4tcHdkMgo=" -i  localhost:8090/authorization
;curl -X POST -H "Authorization: Basic YWRtaW46YWRtaW53cm9uZwo=" -i  localhost:8090/authorization

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
        (if (is-authenticated? bind expected-users) (generate-jwt-token))
        (jwtm/new-token bind 900)
        ))
