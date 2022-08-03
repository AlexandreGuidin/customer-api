(ns api.logic.customer
  (:require
    [api.database.db :as db]
    [schema.core :as s :include-macros true]
    [api.adapters.customer :as adapter]
    [api.models.customer :as model.customer]
    )
  (:import (clojure.lang ExceptionInfo)))

(s/defn find-all :- [model.customer/CustomerResponse]
  []
  (map #(adapter/entity->response %) (db/find-all)))

(s/defn save-customer :- model.customer/CustomerResponse
  [request :- model.customer/CustomerRequest]
  (try
    (-> (s/validate model.customer/CustomerRequest request)
        (adapter/request->entity)
        (db/create-new)
        (last)
        (adapter/entity->response)
        )
    (catch ExceptionInfo e
      (println "Failure while validating request" (.getMessage e))
      nil
      )))

(s/defn find-by-id :- model.customer/CustomerResponse
  [id :- s/Uuid]
  (-> (java.util.UUID/fromString id)
      (db/find-by-id)
      (first)
      (adapter/entity->response)
      ))

(s/defn update-status-by-id :- model.customer/CustomerResponse
  [id :- s/Uuid status :- model.customer/Status]
  (-> (java.util.UUID/fromString id)
      (db/update-status,,, status)
      (adapter/entity->response)
      ))