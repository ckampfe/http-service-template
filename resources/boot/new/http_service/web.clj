(ns {{name}}.web
  (:require [bidi.ring :as bidi-ring]
            [mount.core :refer [defstate]]
            [org.httpkit.server :as http]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [taoensso.timbre :as log :refer [info]]
            [{{name}}.config :as config]
            [{{name}}.controller :as controller]))

(def routes ["/" controller/index])

(def handler
  (-> routes
      bidi-ring/make-handler
      wrap-json-response
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (wrap-defaults api-defaults)))

(defn start-server!
  "Starts an HTTP server using the provided Ring `handler`."
  [config]
  (let [port (config/webserver-port config)]
    (info "Starting server on port" port)
    (http/run-server handler {:port port})))

(defstate server
  :start (start-server! config/config)
  :stop (server :timeout 100))
