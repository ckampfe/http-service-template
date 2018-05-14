(ns {{name}}.web
  (:require [bidi.ring :as bidi-ring]
            [mount.core :refer [defstate]]
            [org.httpkit.server :as http]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [taoensso.timbre :as log :refer [debug error info]]
            [{{name}}.config :as config]
            [{{name}}.controller :as controller]))

(defn request-response-logger [handler]
  (fn [request]
    (let [start (System/currentTimeMillis)
          id (rand-int 0xffff)]
      (try
        (debug request)
        (let [response (handler request)
              end (System/currentTimeMillis)]

          (info id
                "-"
                "HTTP"
                (:status response)
                "-"
                (- end start)
                "millis")

          response)

        (catch Throwable t
          (let [end (System/currentTimeMillis)]
            (error "Uncaught exception processing request:"
                   id
                   "-"
                   "HTTP"
                   "-"
                   (- end start)
                   "millis")
            (error id "-" t)
            (throw t)))))))

(defn not-found [request]
  {:status 404
   :headers {"Content-Type" "text/plain"}})

(def routes ["/" [["" controller/index]
                  [true not-found]]])

(def handler
  (-> routes
      bidi-ring/make-handler
      wrap-json-response
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (wrap-defaults api-defaults)
      request-response-logger))

(defn start-server!
  "Starts an HTTP server using the provided Ring `handler`."
  [config]
  (let [port (config/webserver-port config)]
    (info "Starting server on port" port)
    (http/run-server handler {:port port})))

(defstate server
  :start (start-server! config/config)
  :stop (server :timeout 100))
