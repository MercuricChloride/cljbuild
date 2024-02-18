(ns eth-cljbuild.subs
  (:require
   [re-frame.core :refer [reg-sub]]))

(reg-sub
 ::graph-data
 (fn [db _query-vector]
  (select-keys db [:nodes :edges])))

(reg-sub
 ::context-menu-state
 (fn [db _query-vector]
   (get-in db [:context-menu])))

(reg-sub
 ::editor-panel-state
 (fn [db _query-vector]
   (get-in db [:editor-panel])))

(reg-sub
 ::inputs
 (fn [db query-vector]
   42))

(reg-sub
 ::node-types
 (fn [db _query-vector]
   (select-keys db [:node-types])))
