(ns api.models.customer
  (:require [schema.core :as s :include-macros true]
            [schema.spec.core :as spec :include-macros true]
            [schema.spec.leaf :as leaf :include-macros true]
            [clj-time.core :as time]
            [clj-time.format :as fmt]))

(defrecord DateTime [dateTime fmt]
  s/Schema
  (s/spec [this] (leaf/leaf-spec (spec/precondition [this] #(fmt/unparse (:basic-ordinal-date-time fmt) dateTime) #(list 'fmt/unparse fmt dateTime))))
  (s/explain [this] (list 'fmt/unparse dateTime)))

(def all-status #{:new :disabled})
(s/defschema Status (apply s/enum all-status))

(s/defrecord CustomerEntity
  [
   id :- s/Uuid
   name :- s/Str
   lastName :- s/Str
   status :- Status
   birthDate :- s/Str
   createdAt :- DateTime
   ])

(s/defrecord CustomerRequest
  [
   :id :- s/Str
   :name :- s/Str
   :lastName :- s/Str
   :birthDate :- s/Str
   ])