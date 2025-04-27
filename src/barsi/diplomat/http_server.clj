(ns barsi.diplomat.http-server
  (:require [barsi.helpers.json :as j]
            [barsi.db.dev :as db]
            [barsi.helpers.debbug :as debbug]
            [clojure.pprint :as p]
            [io.pedestal.http :as http]))

(defonce database (atom {}))

(defn- ping [_]
  {:status 200
   :body   (str "Pong")})

(defn- list-item [_]
  ;(db.dev/insert-in-db db.dev/base conj body)
  {:status 200
   :body   (str "items-update")})

(defn create-item [item]
  (try
    ;(db/insert-in-db database conj item)
    {:status 201
     :body   (j/map->json {:message "Item created successfully" :item item})}
    (catch Exception e
      {:status 500
       :body   (j/map->json {:message "Failed to save item"
                                          :error   (.getMessage e)})})))

(def common-interceptors
  [])

(def routes
  ; (route/expand-routes
  #{["/api/ping"
     :get
     ping
     :route-name :ping]

    ["/api/list-item"
     :get
     list-item
     :route-name :list-item]

    ["/api/create-item"
     :post
     (fn [request] (create-item (:body (p/pprint request))))
     :route-name :create-item]})

(def service-map {:env          :prod
                  ::http/routes routes
                  ::http/host   "0.0.0.0"
                  ::http/port   8080
                  ::http/type   :jetty
                  ::http/join?  false})