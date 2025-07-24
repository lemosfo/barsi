(ns barsi.diplomat.http-server
  (:require [barsi.db.dev :as db]
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [barsi.helpers.json :as json]))

(defonce database (atom {}))

(defn- ping [_]
  {:status 200
   :body   (str "Pong")})

(-> (db/get-item database)
    (json/map->json))

(defn- list-item [input]
  (let [filter-id (:id input)
        response {:status 200
                  :body   (db/get-item database)}]
    (try
      (-> response
          (ring-resp/content-type "application/json"))
      (catch Exception e
        {:status 500
         :body   {:message "Failed to retrieve item"
                  :error   (.getMessage e)}}))))

(defn create-new-item-handler [input]
  (let [transaction-id (random-uuid)
        transaction (assoc input :id transaction-id)
        response {:status 201
                  :body   {:message "Your transaction was successfully registered"
                           :id      transaction-id}}]
    (try
      (do (db/insert-in-db database conj transaction)
          (json/map->json response))
      (catch Exception e
        {:status 500
         :body   {:message "Failed to save item"
                  :error   (.getMessage e)}}))))

(def common-interceptors
  [(body-params/body-params)])

(defn create-new-item [request]
  (let [body-data (:json-params request)]
    (-> (ring-resp/response (create-new-item-handler body-data))
        (ring-resp/content-type "application/json"))))

(def routes
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
     (conj common-interceptors `create-new-item)
     :route-name :create-item]})

(def service-map {:env          :prod
                  ::http/routes routes
                  ::http/host   "0.0.0.0"
                  ::http/port   8080
                  ::http/type   :jetty
                  ::http/join?  false})