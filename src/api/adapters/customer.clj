(ns api.adapters.customer
  (:require
    [clj-time.core :as time]
    [clj-time.format :as fmt]))

(defn entity->response
  [{:keys [id, name, lastName, status, birthDate, createdAt]}]
  {
   :id        id
   :name      name
   :lastName  lastName
   :status    status
   :birthDate birthDate
   :createdAt (fmt/unparse (:basic-ordinal-date-time fmt/formatters) createdAt)
   })


(defn request->entity
  [{:keys [name, lastName, birthDate]}]
  {
   :id        (java.util.UUID/randomUUID)
   :name      name
   :lastName  lastName
   :status    "new"
   :birthDate birthDate
   :createdAt (time/now)
   })