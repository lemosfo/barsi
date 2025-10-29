(ns barsi.helper.http
  (:require [schema.core :as s]
            [ring.util.response :refer [response status]]))

(defn wrap-validate-schema
  "Middleware that validates request parameters against a schema.
   If validation fails, returns 400 Bad Request with error details."
  [handler schema]
  (fn [request]
    (let [params (:params request)
          validation-result (s/check schema params)]

      (if validation-result
        ;; Validation failed - return error response
        (-> (response {:error "Invalid request parameters"
                       :details validation-result})
            (status 400))
        ;; Validation passed - call the handler
        (handler request)))))
