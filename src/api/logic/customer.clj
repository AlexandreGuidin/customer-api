(ns api.logic.customer
  (:require
    [api.database.db :as db]
    [schema.core :as s :include-macros true]
    [api.adapters.customer :as adapter]
    [api.models.customer :as model.customer]
    [api.logic.schema :as logic.s]
    )
  (:import (java.util UUID)))

(s/defn find-all :- [model.customer/CustomerResponse]
  []
  (map #(adapter/entity->response %) (db/find-all)))

(s/defn save-customer :- model.customer/CustomerResponse
  [request :- model.customer/CustomerRequest]
  (-> (s/validate model.customer/CustomerRequest request)
      (adapter/request->entity)
      (db/create-new)
      (last)
      (adapter/entity->response)
      ))

(s/defn find-by-id :- model.customer/CustomerResponse
  [id :- s/Uuid]
  (if-some [customer-entity (-> (s/validate model.customer/UUID id)
                                (UUID/fromString)
                                (db/find-by-id)
                                (first))]
    (adapter/entity->response customer-entity)
    (throw (ex-info "entity not found" {:type :not-found-entity}))
    )
  )

(s/defn update-status-by-id :- model.customer/CustomerResponse
  [id :- s/Uuid status :- model.customer/Status]
  (-> (s/validate model.customer/UUID id)
      (UUID/fromString)
      (db/update-status,,, status)
      (adapter/entity->response)
      ))