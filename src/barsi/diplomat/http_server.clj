(ns barsi.diplomat.http-server
  (:require [io.pedestal.http :as http]
            [barsi.db.dev :as db]))

(defn- ping [_]
  {:status 200
   :body   (str "Pong")})

(defn- list-item [_]
  ;(db.dev/insert-in-db db.dev/base conj body)
  {:status 200
   :body   (str "items-update")})

(defn- create-item [_]
  {:status 201
   :body   (str "create-item")})

(def common-interceptors
  [])

(def routes
  ; (route/expand-routes
  #{["/ping"
     :get
     ping
     :route-name :ping]

    ["/list-item"
     :get
     list-item
     :route-name :list-item]

    ["/create-item"
     :post
     create-item
     :route-name :create-item]})

(def service-map {:env          :prod
                  ::http/routes routes
                  ::http/host   "0.0.0.0"
                  ::http/port   8080
                  ::http/type   :jetty
                  ::http/join?  false})