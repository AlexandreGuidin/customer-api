(ns api.diplomat.http-in.customer
  (:require [api.logic.customer :as logic.customer]))

(defn create-customer
  [{:keys [headers params json-params path-params] :as request}]
  {:status 200 :body (logic.customer/save-customer json-params)}
  )

(defn find-all [_]
  {:status 200 :body (logic.customer/find-all)})

(defn find-by-id
  [{:keys [headers params json-params path-params] :as request}]
  {:status 200 :body (logic.customer/find-by-id (:id path-params))}
  )

(defn disable-customer
  [{:keys [headers params json-params path-params] :as request}]
  {:status 200 :body (logic.customer/update-status-by-id (:id path-params) "disabled")}
  )