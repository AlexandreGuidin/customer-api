(ns api.server
  (:require
    [io.pedestal.http :as http]
    [api.routes]
    ))

(defn server-map []
  (http/create-server
    {::http/routes api.routes/routes
     ::http/type   :jetty
     ::http/port   8090}))

(defn start []
  (http/start (server-map)))