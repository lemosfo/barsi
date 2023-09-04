(ns barsi.dev-space
  (:require [barsi.db.dev :as db])
  (:require [clojure.pprint :refer [pprint]]))

(defn print-atom [atom]
  (pprint atom))

(defn insert-value [atom value]
  reset! atom value)

(print-atom db/db-test)
(print-atom db/base)

(db/insert-in-db db/base conj 10)

(insert-value db/base 10)
(pprint db/base)







