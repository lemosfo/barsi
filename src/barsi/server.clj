(ns barsi.server
  (:gen-class)
  (:require [io.pedestal.http :as http]
            [io.pedestal.http :as server]
            [io.pedestal.http.route :as route]
            [barsi.diplomat.http-server :as http-server]))

;; This is an adapted service map, that can be started and stopped
;; From the REPL you can call server/start and server/stop on this service
(defonce runnable-service (server/create-server http-server/service-map))

;; This is some functions to start a server inside an atom to help debug process
(defonce server (atom nil))

(defn start-server []
  (println "\nCreating your server in debugging mode ...")
  (reset! server (server/start (server/create-server http-server/service-map))))

(defn stop-server []
  (server/stop @server))

(defn reset-server []
  (stop-server)
  (start-server))

(defn reset-server []
  (stop-server)
  (start-server))

(defn run-dev
  "The entry-point for 'lein run-dev'"
  [& args]
  (println "\nCreating your [DEV] server...")
  (-> http-server/service-map
      (merge {:env                     :dev
              ;; do not block thread that starts web server
              ::server/join?           false
              ;; Routes can be a function that resolve routes,
              ;;  we can use this to set the routes to be reloadable
              ::server/routes          #(route/expand-routes (deref #'http-server/routes))
              ;; all origins are allowed in dev mode
              ::server/allowed-origins {:creds true :allowed-origins (constantly true)}})
      ;; Wire up interceptor chains
      server/default-interceptors
      server/dev-interceptors
      server/create-server
      server/start))

(defn -main
  "The entry-point for 'lein run'"
  [& args]
  (println "\nCreating your server...")
  (server/start runnable-service))

;; If you package the service up as a WAR,
;; some form of the following function sections is required (for io.pedestal.servlet.ClojureVarServlet).

(defonce servlet (atom nil))

(defn servlet-init
  [_ config]
  ;; Initialize your app here.
  (reset! servlet (server/servlet-init http-server/service-map nil)))

(defn servlet-service
  [_ request response]
  (server/servlet-service @servlet request response))

(defn servlet-destroy
  [_]
  (server/servlet-destroy @servlet)
  (reset! servlet nil))
