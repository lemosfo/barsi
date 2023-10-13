(ns barsi.cash-flow-test
  (:require [clojure.test :refer :all]
            [barsi.logic.cash-flow :as cash-flow]
            [barsi.db.dev :as db]))

(def input
  "{\"description\":\"Apple Watch\",
\"date\":\"2023-08-29\",
\"amount\":3000.0,
\"account\":\"Nubank\",
\"transaction\":\"Mercado Livre\",
\"id\":\"123456\",
\"flags\":[\"Eletronics\",
\"Free\"]}")


(deftest register-financial-input-test
  (testing "return the register of a financial transaction")
  (is (= (-> input
             (cash-flow/register-financial-input)
             (filter @db/base)) [{:description "Apple Watch"
                               :date        "2023-08-29"
                               :amount      3000.00
                               :account     "Nubank"
                               :transaction "Mercado Livre"
                               :id          "123456"
                               :flags       ["Eletronics" "Free"]}])))
