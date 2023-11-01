(ns barsi.db.dev)

(def base (atom []))

(defn insert-in-db [db function request]
  (swap! db function request))