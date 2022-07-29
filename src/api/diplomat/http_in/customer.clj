(ns api.diplomat.http-in.customer
  (:require [api.logic.customer :as logic.customer]))


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

(defn disable-customer [request]
  {:status 200 :body "{}"})