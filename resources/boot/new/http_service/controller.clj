(ns {{name}}.controller
  (:require [{{name}}.config :as config]
            [cheshire.core :as json]
            [taoensso.timbre :as log :refer [debug error info]]))

(defn index [request]
  (debug request)
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body {:data "ok"}})
