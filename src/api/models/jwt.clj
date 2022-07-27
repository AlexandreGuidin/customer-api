(ns api.models.jwt)

(defrecord JwtToken [token, exp])

(defn new-token [token, exp]
  (->JwtToken token exp))
