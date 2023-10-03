(ns barsi.dev-space
  (:require [barsi.db.dev :as db])
  (:require [clojure.pprint :refer [pprint]]
            [clj-http.client :as http-client]))

(defn consult-my-position [])

(http-client/get "http://google.com"
                 {:save-request? true
                  :debug true
                  :headers {:content-type "application/x-www-form-urlencoded"}})

(defn print-atom [atom]
  (pprint atom))

(defn insert-value [atom value]
  reset! atom value)

(print-atom db/db-test)
(print-atom db/base)

(db/insert-in-db db/base conj 10)

(insert-value db/base 10)
(pprint db/base)







