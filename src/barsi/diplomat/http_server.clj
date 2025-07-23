(ns barsi.diplomat.http-server
  (:require [barsi.helpers.json :as j]
            [barsi.db.dev :as db]
            [barsi.helpers.debbug :as debbug]
            [clojure.pprint :as p]
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [clojure.data.json :as json]
            [io.pedestal.http.content-negotiation :as content-negotiation]
            [ring.util.response :as ring-resp]))

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
     :body   {:message "Item created successfully" :item (:item item)}}
    (catch Exception e
      {:status 500
       :body   {:message "Failed to save item"
                :error   (.getMessage e)}})))

(def common-interceptors
  [(body-params/body-params)])

(defn my-post-handler [request]
  (let [body-data (:json-params request)]
    ;; Process body-data here
    (-> (ring-resp/response (create-item body-data))
        (ring-resp/content-type "application/json"))))


(def supported-types ["text/html"
                      "application/edn"
                      "application/json"
                      "text/plain"])

(def content-negotiation-interceptor
  (content-negotiation/negotiate-content supported-types))

(def coerce-body-interceptor
  {:name ::coerce-body
   :leave
   (fn [context]
     (let [accepted (get-in context [:request :accept :field] "text/plain")
           response (get context :response)
           body (get response :body)
           coerced-body (case accepted
                          "text/html" body
                          "text/plain" body
                          "application/edn" (pr-str body)
                          "application/json" (json/write-str body))
           updated-response (assoc response
                              :headers {"Content-Type" accepted}
                              :body coerced-body)]
       (assoc context :response updated-response)))})

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
     (conj common-interceptors `my-post-handler)
     :route-name :create-item]})

(def service-map {:env          :prod
                  ::http/routes routes
                  ::http/host   "0.0.0.0"
                  ::http/port   8080
                  ::http/type   :jetty
                  ::http/join?  false})