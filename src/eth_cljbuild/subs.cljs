(ns eth-cljbuild.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::graph-data
 (fn [db _query-vector]
   (select-keys db [:nodes :edges :node-types])))

(re-frame/reg-sub
 ::inputs
 (fn [db query-vector]
   (println query-vector)
   42))

(re-frame/reg-sub
 ::node-types
 (fn [db _query-vector]
   (select-keys db [:node-types])))
