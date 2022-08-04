(ns api.interceptors.error
  (:require
    [io.pedestal.interceptor.error :as error-interceptor]
    [api.logic.schema :as logic.schema]
    ))

(defn parse-exception-info-response
  [ex]
  (cond
    (= :schema.core/error (:type (ex-data ex))) {:status 400 :body (logic.schema/extract-error-body ex)}
    (= :not-found-entity (:type (ex-data ex))) {:status 404}
    (= :authorization-error (:type (ex-data ex))) {:status 401}
    :else {:status 500 :body {:error (.getMessage ex)}}))

(def interceptor
  (error-interceptor/error-dispatch [ctx ex]

                                    [{:exception-type :clojure.lang.ExceptionInfo}]
                                    (assoc ctx :response (parse-exception-info-response ex))

                                    :else
                                    (assoc ctx :io.pedestal.interceptor.chain/error ex)))