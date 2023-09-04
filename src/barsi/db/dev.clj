(ns barsi.db.dev)

(def base (atom []))

(def db-test (atom {:car :test
                    :airplane :test}))
(defn insert-in-db [db function request]
  (swap! db function request))

(def db-interceptor
  {:name ::database-interceptor
   :enter   (fn [context]
              (update context :request assoc ::database @base))
   :leave   (fn [context]
              (if-let [[op & args] (::tx-data context)]
                (do
                  (apply swap! base op args)
                  (assoc-in context [:request ::database] @base))
                context))})