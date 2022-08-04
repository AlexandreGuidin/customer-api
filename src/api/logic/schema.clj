(ns api.logic.schema
  (:require
    [schema.core :as s :include-macros true]
    [schema.utils :as sutils]
    [clojure.string :as str])
  (:import (clojure.lang ExceptionInfo)))

(defn extract-error-with-regex
  [error]
  (->> error
       (str)
       (re-matcher #"\(not \(([^)]+)( [\D\d]+)\)\)",,,)
       (re-find)
       (second)
       ))

(defn extract-single-error
  [error]
  (if (= (type error) schema.utils.ValidationError)
    (let [error-explained (sutils/validation-error-explain error)]
      (cond
        (str/includes? error-explained "not") (extract-error-with-regex error-explained)
        :else error-explained
        )
      )
    error
    )
  )

(defn extract-errors-from-map
  [[key value]]
  {key (extract-single-error value)})

(defn extract-errors
  [error-data]
  (if (map? error-data)
    (vec (map extract-errors-from-map error-data))
    [(extract-single-error error-data)]))

(s/defn extract-error-body
  [ex :- ExceptionInfo]
  {:errors (-> ex
               (ex-data)
               (:error)
               (extract-errors)
               )})