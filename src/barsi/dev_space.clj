(ns barsi.dev-space
  (:require [barsi.db.dev :as db]
            [barsi.helpers :as json]
            [clojure.pprint :refer [pprint]]
            [barsi.logic.cash-flow :as sub]
            [barsi.helpers :as json]))

(def input
  "{\"description\":\"Apple Watch\",
\"date\":\"2023-08-29\",
\"amount\":3000.0,
\"account\":\"Nubank\",
\"transaction\":\"Mercado Livre\",
\"id\":\"123456\",
\"flags\":[\"Eletronics\",
\"Free\"]}")

(sub/register-financial-input input)

(defn print-atom [atom]
  (pprint atom))

(sub/get-register-financial :id "123456")

(print-atom db/base)

(defn filter-by-id [data-to-filter id-to-match]
  (filter #(= id-to-match (:id %)) data-to-filter))

(def filtered-data (filter-by-id data "12345"))


(def fake-atom [{:a 12 :id 22} {:a 13 :id 33} {:a 16 :id 44}])

(pprint fake-atom)

(defn get-register-financial [parameter value]
  (filter #(= (:id %) value) fake-atom))

(get-register-financial :id 33)



