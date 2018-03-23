(def project '{{name}})
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :dependencies   '[[aero                                          "1.1.3"] ;; config management
                            [aleph                                         "0.4.4"] ;; http server
                            [bidi                                          "2.1.3"] ;; router
                            [cheshire                                      "5.8.0"] ;; json
                            [com.fzakaria/slf4j-timbre                     "0.3.8"] ;; logging dep
                            [com.taoensso/timbre                          "4.10.0"] ;; logging
                            [mount                                        "0.1.12"] ;; state management
                            [org.clojure/clojure                           "1.9.0"]
                            [ring/ring-defaults                            "0.3.1"] ;; default middleware stack
                            [adzerk/boot-test "RELEASE" :scope "test"]])

(task-options!
 aot {:namespace   #{'{{name}}.core}}
 pom {:project     project
      :version     version
      :description "FIXME: write description"
      :url         "http://example/FIXME"
      :scm         {:url "https://github.com/yourname/{{name}}"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}}
 jar {:main        '{{name}}.core
      :file        (str "{{name}}-" version "-standalone.jar")})

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot) (pom) (uber) (jar) (target :dir dir))))

(deftask run
  "Run the project."
  [a args ARG [str] "the arguments for the application."]
  (require '[{{name}}.core :as app])
  (apply (resolve 'app/-main) args))

(deftask viz
  "Visualize the project."
  []
  (let [pod (boot.pod/make-pod
             (update-in (get-env) [:dependencies]
                        concat
                        '[[org.clojure/tools.namespace "0.3.0-alpha4"]
                          [aysylu/loom                 "1.0.0"]]))]

    (boot.pod/with-eval-in pod
      (require '[clojure.java.io :as io])
      (require '[clojure.tools.namespace.find :as ns-find])
      (require '[clojure.tools.namespace.parse :as ns-parse])
      (require '[loom.graph :as g])
      (require '[loom.io :as lio])
      (let [ns-declarations (ns-find/find-ns-decls-in-dir (clojure.java.io/file "./src"))
            ns-names (map second ns-declarations)
            ns-names-set (set ns-names)
            deps (map (comp (partial clojure.set/intersection ns-names-set)
                            ns-parse/deps-from-ns-decl)
                      ns-declarations)
            ns-with-deps (zipmap ns-names deps)]
        (->> ns-with-deps
             g/digraph
             lio/view))
      nil)))

(require '[adzerk.boot-test :refer [test]])
