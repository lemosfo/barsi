(ns barsi.db.dev
  (:require [clojure.pprint :refer [pprint]]))

(def base (atom []))

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