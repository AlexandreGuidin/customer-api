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

(defn update-status
  [id status]
  (let [index (first (keep-indexed #(if (= id (:id %2)) %1) @customer-table))]
    (when (nil? index) (throw (ex-info "Could not find customer" {:type :not-found-customer})))
    (swap! customer-table update-in [index :status] (fn [_] status))
    (@customer-table index)))