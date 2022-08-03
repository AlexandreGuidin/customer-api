(ns api.models.customer
  (:require [schema.core :as s :include-macros true]
            [schema.spec.core :as spec :include-macros true]
            [schema.spec.leaf :as leaf :include-macros true]
            [clojure.string :as str])
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

(def ZonedDateTimeSchema (s/pred is-valid-zoned-datetime? '"format should be '2011-12-03T10:15:30+01:00'"))
(def LocalDateSchema (s/pred is-valid-localdate? '"format should be '2011-12-03'"))

(def all-status #{:new :disabled})
;(s/defschema Status (apply s/enum all-status))
(def Status (s/pred #(contains? all-status %) (str "value is not in " (list all-status))))

(s/defschema CustomerEntity
  {:id        s/Uuid
   :name      s/Str
   :lastName  s/Str
   :status    Status
   :birthDate LocalDateSchema
   :createdAt ZonedDateTimeSchema}
  )

(s/defschema CustomerRequest
  {:name      s/Str
   :lastName  s/Str
   :birthDate s/Str
   })

(s/defschema CustomerResponse
  {:id        s/Str
   :name      s/Str
   :lastName  s/Str
   :status    s/Str
   :birthDate s/Str
   :createdAt s/Str})
