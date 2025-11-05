(ns barsi.wire.in.item
  (:require [schema.core :as s]))

(s/defschema Item
             "An input of financial item"
             {:account                      s/Int
              :amount                       s/Num
              (s/optional-key :description) s/Str
              (s/optional-key :tags)        [s/Str]})
