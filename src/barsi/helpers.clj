(ns barsi.helpers
  (:require [cheshire.core :as j]))

(defn json->map
  "Function to convert a json in a map"
  [json]
  (j/parse-string json true))

(defn map->json
  "Function to convert a map in a json"
  [json]
  (j/generate-string json))


;(defn format-string-to-date
;  "Function to convert string to date format"
;  [transaction]
;  (let [date (t/to-local-date-time (get-in transaction [:transaction :time]))]
;    (update-in transaction [:transaction] assoc :time date)))

;(defn get-violations
;  "Function that just recieve information to build output responses"
;  [response-reason]
;  (get model/returns-map response-reason))

;(defn create-output-response
;  "Function that just recieve information to build output responses"
;  [account response-reason]
;  (conj account [:violations (get-violations response-reason)]))
;(defn extract-value-from-account
;  "Function to extract values from account, just put key of value desired
;   [key] example :active-card"
;  [key db]
;  (get-in @db [:account key]))
