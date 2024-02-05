(ns eth-cljbuild.events
  (:require
   [re-frame.core :as re-frame]
   [eth-cljbuild.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-fx
 :update-count
 (fn [coeffects [_ count]]
   (let [db (:db coeffects)]
     {:db (assoc db :count count)})))
