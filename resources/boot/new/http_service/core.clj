(ns {{name}}.core
  (:require [clojure.java.jdbc :as jdbc]
            [{{name}}.config :as config]
            [{{name}}.db :as db]
            [{{name}}.web :refer [server]]
            [mount.core :as mount]
            [taoensso.timbre :as log :refer [debug info error]])
  (:gen-class))

(defn set-default-uncaught-exception-handler! []
  (Thread/setDefaultUncaughtExceptionHandler
   (reify Thread$UncaughtExceptionHandler
     (uncaughtException [_ thread ex]
       (error ex "Uncaught exception on" (.getName thread))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  (set-default-uncaught-exception-handler!)

  (info "starting service in" (config/load-profile) "environment")

  (info "loading config")
  (mount/start #'config/config)

  (info "setting log level to" (config/log-level config/config))
  (log/set-level! (config/log-level config/config))

  (info "starting rest of dependencies")
  (mount/start)

  (let [test-query "SELECT 1"]
    (info "running test query:" test-query)
    (jdbc/with-db-connection [conn {:datasource db/datasource}]
      (pr-str (jdbc/query conn test-query)))))
