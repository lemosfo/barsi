(ns barsi.diplomat.http-server
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.test :as test])
  (:use [clojure.pprint]))

(defn ping [_]
  {:status 200
   :body
   (str "Pong")
   })

(defn create-item
  [_]
  {:status 200
   :body   {:text (str "item-1")}})

(def common-interceptors
  [])

(def routes
  ;  (route/expand-routes
  #{["/api/ping"
     :get
     ping
     :route-name :ping]

    ["/api/create-item"
     :get
     blocking-task
     :route-name :create-item]
    })
;)

(def service-map {:env          :prod
                  ::http/routes routes
                  ::http/host   "0.0.0.0"
                  ::http/port   8080
                  ::http/type   :jetty
                  ::http/join?  false})

;Old format to start server
;(defn start-service []
;  (http/start (http/create-server service-map)))

;(defn start-service []
;  (http/start (http/create-server service-map)))


;----------------------------------------------------------------------------------------------------------------------
;;Test Request

;(defonce server (atom nil))

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
;  (test/response-for (::http/service-fn @server) verb url))
;
;(start-server)
;;(stop-server)
;;(reset-server)
;(test-request :get "/api/ping")
;(test-request :get "/api/blocking-task")