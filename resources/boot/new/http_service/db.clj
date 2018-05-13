(ns {{name}}.db
  (:require [hikari-cp.core :as hikari]
            [mount.core :refer [defstate]]
            [taoensso.timbre :as log :refer [info]]
            [{{name}}.config :as config]))

(defstate datasource
  :start (do
           (info "Starting db connection pool")
           (hikari/make-datasource (config/datasource-options config/config)))
  :stop (do
          (info "Closing db connection pool")
          (hikari/close-datasource datasource)))
