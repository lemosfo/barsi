(ns barsi.wire.in.item
  (:require [schema.core :as s]))

(s/defschema Item
  "An input of financial item"
  {(s/required-key :account) s/Int
   (s/required-key :amount)  s/Num
   :description s/Str
   :tags [s/Str]})
