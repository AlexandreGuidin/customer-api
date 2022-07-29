(ns api.diplomat.http-in.customer
  (:require [api.logic.customer :as logic.customer])
  (:import (clojure.lang ExceptionInfo)))


(defn create-customer
  [{:keys [headers params json-params path-params] :as request}]
  {:status 200 :body (logic.customer/save-customer json-params)})

(defn find-all [_]
  {:status 200 :body (logic.customer/find-all)})

(defn find-by-id [{:keys [headers params json-params path-params] :as request}]
  (let [user (logic.customer/find-by-id (:id path-params))]
    (if (nil? user)
      {:status 404}
      {:status 200 :body user}
      ))
  )

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