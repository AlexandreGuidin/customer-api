(ns api.adapters.customer
  (:require
    [schema.core :as s :include-macros true]
    [api.models.customer :as model.customer]
    )
  (:import (java.time ZonedDateTime)
           (java.time.format DateTimeFormatter)))

(s/defn entity->response :- model.customer/CustomerResponse
  [{:keys [id, name, lastName, status, birthDate, createdAt] :as entity}]
  {
   :id        id
   :name      name
   :lastName  lastName
   :status    status
   :birthDate birthDate
   :createdAt (.format createdAt (DateTimeFormatter/ISO_OFFSET_DATE_TIME))
   })


(s/defn request->entity :- model.customer/CustomerEntity
  [{:keys [name, lastName, birthDate] :as request}]
  {
   :id        (random-uuid)
   :name      name
   :lastName  lastName
   :status    :new
   :birthDate birthDate
   :createdAt (ZonedDateTime/now)
   })