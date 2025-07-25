(defproject barsi "0.1.0-SNAPSHOT"
  :description "Service to create financial items"
  :url "http://myapp.com/:service-name/api/:route"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :repositories {:url "https://repo1.maven.org"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [io.pedestal/pedestal.service "0.5.10"]
                 [io.pedestal/pedestal.immutant "0.5.10"]
                 [org.slf4j/slf4j-simple "2.0.3"]
                 [cheshire "5.11.0"]
                 [clj-http "3.9.1"]
                 [org.clojure/data.json "2.5.1"]]
  :main ^{:skip-aot false} barsi.server
  :profiles {:dev     {:dependencies [[io.pedestal/pedestal.service-tools "0.5.11-beta-1"]
                                      [io.pedestal/pedestal.jetty "0.5.10"]]}
             :uberjar {:aot [barsi.server]}}
  :repl-options {:init-ns barsi.core})
