(ns api.models.customer
  (:require [schema.core :as s :include-macros true]
            [schema.spec.core :as spec :include-macros true]
            [schema.spec.leaf :as leaf :include-macros true]
            )
  (:import (java.time.format DateTimeFormatter)
           (java.time ZonedDateTime LocalDate)))

(defn is-valid-zoned-datetime?
  [dateTime]
  (try (not (nil? (ZonedDateTime/parse dateTime (DateTimeFormatter/ISO_OFFSET_DATE_TIME))))
       (catch Exception ex
         (println "Invalid date with error" (.getMessage ex))
         'false)))

(defn is-valid-localdate?
  [dateTime]
  (try (not (nil? (LocalDate/parse dateTime (DateTimeFormatter/ISO_DATE))))
       (catch Exception ex
         (println "Invalid date with error" (.getMessage ex))
         'false)))

(def ZonedDateTimeSchema (s/pred is-valid-zoned-datetime? '"not in format '2011-12-03T10:15:30+01:00'"))
(def LocalDateSchema (s/pred is-valid-localdate? '"not in format '2011-12-03'"))

(def all-status #{:new :disabled})
(s/defschema Status (apply s/enum all-status))

(s/defrecord CustomerEntity
  [
   id :- s/Uuid
   name :- s/Str
   lastName :- s/Str
   status :- Status
   birthDate :- LocalDateSchema
   createdAt :- ZonedDateTimeSchema
   ])

(s/defrecord CustomerRequest
  [
   id :- s/Uuid
   name :- s/Str
   lastName :- s/Str
   birthDate :- s/Str
   ])

(s/defrecord CustomerResponse
  [
   id :- s/Str
   name :- s/Str
   lastName :- s/Str
   status :- s/Str
   birthDate :- s/Str
   createdAt :- s/Str
   ])

(s/validate CustomerEntity (->CustomerEntity #uuid "a7e4e755-7232-4012-931b-8bbe765773a4" "name" "last name" :new "2022-01-30" "2022-03-30T10:15:30-03:00"))
