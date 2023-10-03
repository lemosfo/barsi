(ns barsi.logic.items-test
  (:require [clojure.test :refer :all]))

(def input {:transaction-id {:date        "yyyy-mm-dd-mm:hh"
                             :amount      1000.00
                             :description "Bunch of characters"
                             :account     "Nubank"
                             :flags       ["Reserve"]}})
(deftest register-financial-input-test
  (testing "return the register of a financial transaction")
  (is (= (cash-flow/register-financial-input input) {:transaction-id {:date        "yyyy-mm-dd-mm:hh"
                                                                      :amount      1000.00
                                                                      :description "Bunch of characters"
                                                                      :account     "Nubank"
                                                                      :flags       ["Reserve"]}})))
