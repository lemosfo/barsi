(ns barsi.diplomat.http-server
  (:require [io.pedestal.http :as http]
            [barsi.logic.financial-calculates :as financial-calculates]
            [io.pedestal.test :as test]
            [barsi.db.dev :as db.dev]
            [barsi.helpers :as helpers]))

(defn- ping [_]
  {:status 200
   :body   (str "Pong")})

(defn- create-item [req]
  (let [body (-> req)]
    (db.dev/insert-in-db db.dev/base conj body)
    {:status 200
     :body   @db.dev/base}))

(def common-interceptors
  [])

(def routes
  ; (route/expand-routes
  #{["/api/ping"
     :get
     ping
     :route-name :ping]

    ["/api/create-item"
     :post
     create-item
     :route-name :create-item]})

(def service-map {:env          :prod
                  ::http/routes routes
                  ::http/host   "0.0.0.0"
                  ::http/port   8080
                  ::http/type   :jetty
                  ::http/join?  false})

;Old format to start server
;(defn start-service []
;  (http/start (http/create-server service-map)))

;(start-service)

;(defn start-service []
;  (http/start (http/create-server service-map)))

;----------------------------------------------------------------------------------------------------------------------
;;Test Request

;(defonce server (atom nil))
;
;(defn start-server []
;  (reset! server (http/start (http/create-server service-map))))
;
;(defn stop-server []
;  (http/stop @server))
;
;(defn reset-server []
;  (stop-server)
;  (start-server))
;
;(defn test-request [verb url]
;(test/response-for (::http/service-fn @server) verb url))
;
;(start-server)
;;(stop-server)
;(reset-server)
;(test-request :get "/api/ping")
;(test-request :get "/api/create-item")