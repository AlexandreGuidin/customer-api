(ns api.logic.schema
  (:require
    [schema.core :as s :include-macros true]
    [schema.utils :as sutils])
  (:import (clojure.lang ExceptionInfo)))

(defn extract-error
  [error]
  (->> error
       (sutils/validation-error-explain)
       (str)
       (re-matcher #"\(not \(([^)]+)( [\D\d]+)\)\)",,,)
       (re-find)
       (second)
       )
  )

(defn validation-error-as-string
  [[key value]]
  {key (extract-error value)})

(defn validate-schema
  [schema values]
  (try (s/validate schema values)
       (catch ExceptionInfo ex
         (let [data (ex-data ex)
               error-data (:error data)
               errors (if (map? error-data) (map validation-error-as-string error-data) (extract-error error-data))]

           (throw (ex-info "Schema is not valid" {:errors errors :type "validation-error"}))
           )
         )))

