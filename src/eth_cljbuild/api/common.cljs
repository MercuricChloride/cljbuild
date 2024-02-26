(ns eth-cljbuild.api.common
  (:require
   [eth-cljbuild.db :refer [default-db]]
   [re-frame.core :refer [reg-event-db]]))

(reg-event-db
 ::initialize-db
 (fn [_ _]
   default-db))
