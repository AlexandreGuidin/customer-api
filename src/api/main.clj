(ns api.main
  (:require [api.server]))

(defn -main
  [& args]
  (println "Starting server..." args)
  (api.server/start))
