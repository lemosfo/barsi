(ns barsi.diplomat.http-server
  (:require [io.pedestal.http :as http]
            [barsi.logic.cash-flow :as cash-flow]
            [barsi.helpers :as helpers]))

(defn- ping [_]
  {:status 200
   :body   (str "Pong")})

(defn- list-item [request]
  (let [params (:params request)
        id (-> params :parameters :id)]
    ;(db.dev/insert-in-db db.dev/base conj body)
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body   (cash-flow/get-register-financial :id id)}))

(defn- create-item [request]
  (let [body (slurp (:body request))
        data (helpers/json->map body)]
    (http/json-response {:status 201
                         :headers {"Content-Type" "application/json"}
                         :body   (str "created:" request)
                         #_(cash-flow/register-financial-input body)})))

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