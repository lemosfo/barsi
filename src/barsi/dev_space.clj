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
\"id\":\"12345\",
\"flags\":[\"Eletronics\",
\"Free\"]}")

(sub/register-financial-input input)

(defn print-atom [atom]
  (pprint atom))

(sub/get-register-financial :id 12345)

(print-atom db/base)








