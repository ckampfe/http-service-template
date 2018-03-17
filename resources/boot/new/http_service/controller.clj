(ns {{name}}.controller
  (:require [{{name}}.config :as config]
            [taoensso.timbre :as log :refer [error info]]))

(defn index [request]
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body "ok"})
