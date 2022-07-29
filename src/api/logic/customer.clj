(ns api.logic.customer
  (:require
    [api.database.db :as db]
    [api.adapters.customer :as adapter]
    ))

(defn find-all
  []
  (map #(adapter/entity->response %) (db/find-all)))

(defn save-customer
  [request]
  (-> request
      (adapter/request->entity)
      (db/create-new)
      (first)
      (adapter/entity->response)
      ))

(defn find-by-id
  [id]
  (-> (java.util.UUID/fromString id)
      (db/find-by-id)
      (first)
      (adapter/entity->response)
      ))

(defn update-status-by-id
  [id status]
  (-> (java.util.UUID/fromString id)
      (db/update-status ,,, status)
      (adapter/entity->response)
      ))