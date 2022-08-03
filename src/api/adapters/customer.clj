(ns api.adapters.customer
  (:require
    [schema.core :as s :include-macros true]
    [clj-time.core :as time]
    [clj-time.format :as fmt]
    )
  (:import (api.models.customer CustomerEntity)))

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


(s/defn request->entity :- CustomerEntity
  [{:keys [name, lastName, birthDate]}]
  {
   :id        (java.util.UUID/randomUUID)
   :name      name
   :lastName  lastName
   :status    "new"
   :birthDate birthDate
   :createdAt (time/now)
   })