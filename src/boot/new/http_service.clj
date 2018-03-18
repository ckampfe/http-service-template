(ns boot.new.http-service
  (:require [boot.new.templates :refer [renderer name-to-path ->files]]))

(def render (renderer "http-service"))

(defn http-service
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (println "Generating fresh 'boot new' http-service project.")
    (->files data
             ["build.boot" (render "build.boot" data)]
             ["resources/config.edn" (render "config.edn" data)]
             ["resources/dev-config.edn" (render "dev-config.edn" data)]
             ["src/{{sanitized}}/config.clj" (render "config.clj" data)]
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)]
             ["src/{{sanitized}}/web.clj" (render "web.clj" data)]
             ["src/{{sanitized}}/controller.clj" (render "controller.clj" data)]
             ["test/{{sanitized}}/core_test.clj" (render "core_test.clj" data)]
             [".gitignore" (render ".gitignore" data)])))
