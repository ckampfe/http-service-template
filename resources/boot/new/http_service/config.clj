(ns {{name}}.config
  (:require [aero.core :as aero]
            [clojure.java.io :as io]
            [mount.core :refer [defstate]]))

;;
;; config loaders
;;

(defn webserver-port [config]
  (get-in config [:webserver :port]))

(defn log-level [config]
  (get-in config [:log :level]))

(defn datasource-options [config]
  (get-in config [:datasource-options]))

;;
;; config/environment plumbing
;;

(defn load-config [profile]
  (aero/read-config (io/resource "config.edn")
                    {:profile (or profile :dev)}))

(defn load-profile
  ([]
   (load-profile (System/getenv "CLJ_ENV")))
  ([env] (case env
           "dev"         :dev
           "development" :dev
           "test"        :test
           "prod"        :prod
           "production"  :prod
           :dev ;; default to `:dev`
)))

(defstate config
  :start (load-config (load-profile))
  :stop {})
