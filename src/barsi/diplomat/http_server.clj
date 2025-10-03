(ns barsi.diplomat.http-server
  (:require [barsi.db.dev :as db]
            [barsi.helpers.json :as json]
            [barsi.wire.in.item :as w.in.item]
            [schema.core :as s]
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]))

(defonce database (atom {}))

(defn- ping [_]
  {:status 200
   :body   (str "Pong")})

(defn- list-item-handler [input]
  (let [filter-id (:id input)
        response {:status 200
                  :body   (db/get-item database)}]
    (try
      (-> response
          (json/map->json))
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

(defn list-item [request]
  (let [body-data (:json-params request)]
    (-> (ring-resp/response (list-item-handler body-data))
        (ring-resp/content-type "application/json"))))

(s/defn create-new-item
  [request :- w.in.item/Item]
  (let [body-data (:json-params request)]
    (-> (ring-resp/created (create-new-item-handler body-data))
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