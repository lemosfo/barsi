(ns barsi.logic.cash-flow
  (:require [barsi.helpers :as json]
            [barsi.db.dev :as db])
  (:use [clojure.pprint]))

(defn register-financial-input [input]
  (->> input
       (json/json->map)
       (db/insert-in-db db/base conj)))

(defn get-register-financial [parameter value]
  (filter #(= (parameter %) value) @db/base))