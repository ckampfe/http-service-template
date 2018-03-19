(ns {{name}}.core
  (:require [{{name}}.config :as config]
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
  (info "starting dependencies!")
  (mount/start)                       ;; start dependencies
  (log/set-level! (config/log-level config/config))
  (info "set log level to" (config/log-level config/config))
  (info "starting server!")
  (aleph.netty/wait-for-close server)) ;; start server and block
