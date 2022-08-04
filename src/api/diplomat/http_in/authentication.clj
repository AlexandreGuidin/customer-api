(ns api.diplomat.http-in.authentication
  (:require
    [api.controllers.authentication :as controller.authentication]
    [clojure.string :as str])
  )

(def unauthorized {:status 401})

(defn post-authentication [request]
  (let [
        headers (:headers request)
        authorization-header (get headers "authorization" "")
        basic-auth (str/trim (str/replace authorization-header "Basic" ""))
        ]
    (cond
      (nil? authorization-header) unauthorized
      (not (str/starts-with? authorization-header "Basic")) unauthorized
      (str/blank? basic-auth) unauthorized
      :else {:status 200 :body (controller.authentication/authenticate basic-auth)}
      )
    )
  )