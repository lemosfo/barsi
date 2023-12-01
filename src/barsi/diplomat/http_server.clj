(ns barsi.diplomat.http-server
  (:require [io.pedestal.http :as http]
            [barsi.db.dev :as db]
            [barsi.logic.cash-flow :as cash-flow]))

(defn- ping [_]
  {:status 200
   :body   (str "Pong")})

(defn- list-item [request]
  ;(db.dev/insert-in-db db.dev/base conj body)
  #_(let [params (:params request)
        parameter (:parameter params)
        id (:id params)])
    {:status 200
     :body   (cash-flow/get-register-financial :id 123)})

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
     #_(list-item {:parameters [:parameter :id]})
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