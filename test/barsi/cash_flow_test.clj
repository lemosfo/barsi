(ns barsi.cash-flow-test
  (:require [clojure.test :refer :all]
            [barsi.logic.cash-flow :as cash-flow]))

(def input
  "{\"description\":\"Apple Watch\",
\"date\":\"2023-08-29\",
\"amount\":3000.0,
\"account\":\"Nubank\",
\"transaction\":\"Mercado Livre\",
\"id\":\"123456\",
\"type\":\"Deposit\",
\"flags\":[\"Eletronics\",\"Free\"]}")

(def input-2
  "{\"description\":\"Cabo HDMI\",
\"date\":\"2023-10-31\",
\"amount\":5000.25,
\"account\":\"Wallet\",
\"type\":\"Withdrawal\",
\"transaction\":\"Mercado Livre\",
\"id\":\"123\",
\"flags\":[\"Eletronics\",\"Free\"]}")

(deftest register-financial-input-test
  (testing "return the register of a financial transaction"
    (is (= (-> input
               (cash-flow/register-financial-input)) [{:description "Apple Watch"
                                                       :date        "2023-08-29"
                                                       :amount      3000.00
                                                       :account     "Nubank"
                                                       :type        "Deposit"
                                                       :transaction "Mercado Livre"
                                                       :id          "123456"
                                                       :flags       ["Eletronics" "Free"]}]))))

(deftest get-register-financial-test
  (let [_ (cash-flow/register-financial-input input)
        _ (cash-flow/register-financial-input input-2)]
    (testing "return an input registered previously using :id as reference"
      (is (= (cash-flow/get-register-financial :id "123") [{:description "Cabo HDMI"
                                                            :date        "2023-10-31"
                                                            :amount      5000.25
                                                            :account     "Wallet"
                                                            :type        "Withdrawal"
                                                            :transaction "Mercado Livre"
                                                            :id          "123"
                                                            :flags       ["Eletronics" "Free"]}])))
    (testing "Return an input registered previously using another key as reference"
      (is (= (cash-flow/get-register-financial :type "Withdrawal") [{:description "Cabo HDMI"
                                                                     :date        "2023-10-31"
                                                                     :amount      5000.25
                                                                     :account     "Wallet"
                                                                     :type        "Withdrawal"
                                                                     :transaction "Mercado Livre"
                                                                     :id          "123"
                                                                     :flags       ["Eletronics" "Free"]}])))))
