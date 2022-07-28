(ns api.interceptors.auth
  (:require [api.logic.authorization :as logic.authorization]
            [clojure.string :as str])
  (:import (clojure.lang ExceptionInfo)))

(defn find-token-in-context
  [context]
  (as-> context bind
        (or (get-in context [:request :headers "authorization"]) "")
        (re-find #"(?i)^Bearer (.+)$" bind)
        (last bind)
        (if (str/blank? bind)
          (throw (ex-info "Could not find a token" {:type :authentication-error}))
          bind
          )
        ))


(defn apply-unauthorized
  [context] (assoc context :response {:status 401 :headers {}}))

(defn authorization-interceptor
  [context]
  (try (let [token (find-token-in-context context)]
         (if (logic.authorization/token-valid? token)
           context
           (apply-unauthorized context)))
       (catch ExceptionInfo _ (apply-unauthorized context))))



(def auth-interceptor
  {:name  ::auth-interceptor
   :enter (fn [context] (authorization-interceptor context))})