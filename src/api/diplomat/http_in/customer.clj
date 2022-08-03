(ns api.diplomat.http-in.customer
  (:require [api.logic.customer :as logic.customer])
  (:import (clojure.lang ExceptionInfo)))


(defn create-customer
  [{:keys [headers params json-params path-params] :as request}]
  (if-let [response (logic.customer/save-customer json-params)]
    {:status 200 :body response}
    {:status 400}
    ))

(defn find-all [_]
  {:status 200 :body (logic.customer/find-all)})

(defn find-by-id [{:keys [headers params json-params path-params] :as request}]
  (if-let [user (logic.customer/find-by-id (:id path-params))]
    {:status 200 :body user}
    {:status 404}))

(defn disable-customer [{:keys [headers params json-params path-params] :as request}]
  (try (let [body (logic.customer/update-status-by-id (:id path-params) "disabled")]
         {:status 200 :body body}
         )
       (catch ExceptionInfo e
         (if (= (:type e) :not-found-customer)
           {:status 404}
           {:status 500}
           )
         ))
  )