(ns api.controllers.authentication
  (:require
    [api.logic.authentication :as logic.authentication]
    ))

(defn authenticate [basic-auth]
  (logic.authentication/basic-auth basic-auth))



