(ns api.interceptors.json
  (:require
    [clojure.data.json :as json]
    [io.pedestal.http.content-negotiation :as content-negotiation]
    ))

(def json-type "application/json")
(def supported-types [json-type])
(def content-interceptor (content-negotiation/negotiate-content supported-types))

(defn- transform-to
  [response]
  (-> response
      (update,,, :body #(json/write-str %))
      (assoc-in [:headers "Content-Type"] json-type)))

(def transform-body
  {:name ::coerce-body
   :leave
   (fn [context]
     (if (get-in context [:response :headers "Content-Type"])
       context
       (update-in context [:response] transform-to)))})
