(ns api.models.customer
  (:require [schema.core :as s :include-macros true])
  (:import (java.time.format DateTimeFormatter)
           (java.time ZonedDateTime LocalDate)))

(defn is-valid-zoned-datetime?
  [dateTime]
  (try (not (nil? (ZonedDateTime/parse dateTime (DateTimeFormatter/ISO_OFFSET_DATE_TIME))))
       (catch Exception _ false)))

(defn is-valid-localdate?
  [dateTime]
  (try (not (nil? (LocalDate/parse dateTime (DateTimeFormatter/ISO_DATE))))
       (catch Exception _ false)))

(def ZonedDateTimeSchema (s/pred is-valid-zoned-datetime? 'not-in-format-2011-12-03T10:15:30+01:00))
(def LocalDateSchema (s/pred is-valid-localdate? 'not-in-format-2011-12-03))

(def all-status #{:new :disabled})
(def Status (s/pred #(contains? all-status %) 'wrong-status-value))

(defn is-valid-uuid?
  [uuid]
  (try (not (nil? (->> uuid
                       (java.util.UUID/fromString)
                       (s/validate s/Uuid))))
       (catch Exception _ false)))

(def UUID (s/pred is-valid-uuid? 'is-not-a-valid-uuid))

(s/defschema CustomerEntity
  {:id        UUID
   :name      s/Str
   :lastName  s/Str
   :status    Status
   :birthDate LocalDateSchema
   :createdAt ZonedDateTimeSchema}
  )

(s/defschema CustomerRequest
  {:name      s/Str
   :lastName  s/Str
   :birthDate LocalDateSchema
   })

(s/defschema CustomerResponse
  {:id        s/Str
   :name      s/Str
   :lastName  s/Str
   :status    s/Str
   :birthDate s/Str
   :createdAt s/Str})
