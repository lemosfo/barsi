(ns barsi.logic.cash-flow
  (:require [barsi.helpers :as json]
            [barsi.db.dev :as db])
  (:use [clojure.pprint]))

(def input "{\"description\":\"Apple Watch\",\"date\":\"2023-08-29\",\"amount\":3000.0,\"account\":\"Nubank\",\"transaction\":\"Mercado Livre\",\"id\":\"123456\",\"flags\":[\"Eletronics\",\"Free\"]}")

(defn register-financial-input [input]
  (->> input
       (json/json->map)
       (db/insert-in-db db/base conj)))

(register-financial-input input)
(pprint db/base)
