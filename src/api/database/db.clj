(ns api.database.db)

(def customer-table (atom []))

(defn find-all
  []
  @customer-table)

(defn find-by-id
  [id]
  (filter #(= (:id %) id) @customer-table))

(defn create-new
  [entity]
  (swap! customer-table conj entity))

(defn delete-all
  []
  (reset! customer-table []))