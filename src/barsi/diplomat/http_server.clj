(ns barsi.diplomat.http-server
  (:require [io.pedestal.http :as http]
            [barsi.logic.financial-calculates :as financial-calculates]
            [io.pedestal.test :as test]
            [barsi.db.dev :as db.dev]
            [barsi.helpers :as helpers]))

(defn- ping [_]
  {:status 200
   :body   (str "Pong")})

(defn- list-items [_]
    ;(db.dev/insert-in-db db.dev/base conj body)
    {:status 200
     :body   (str "items-update")})

(def common-interceptors
  [])

(def routes
  ; (route/expand-routes
  #{["/api/ping"
     :get
     ping
     :route-name :ping]

    ["/api/list-items"
     :get
     list-items
     :route-name :create-item]})

(def service-map {:env          :prod
                  ::http/routes routes
                  ::http/host   "0.0.0.0"
                  ::http/port   8080
                  ::http/type   :jetty
                  ::http/join?  false})