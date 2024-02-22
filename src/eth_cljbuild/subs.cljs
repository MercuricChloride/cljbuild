(ns eth-cljbuild.subs
  (:require
   [eth-cljbuild.utils :refer [->clj ->js]]
   [re-frame.core :refer [reg-sub]]))

(reg-sub
 ::graph-data
 (fn [db _query-vector]
  (select-keys db [:nodes :edges])))

(reg-sub
 ::node-data
 (fn [db [_ node-id]]
   (get-in db [:nodes node-id])))

(reg-sub
 ::rf-instance
 (fn [db _query-vector]
  (:rf-instance db)))

(reg-sub
 ::context-menu-state
 (fn [db _query-vector]
   (get-in db [:context-menu])))

(reg-sub
 ::editor-panel-state
 (fn [db _query-vector]
   (get-in db [:editor-panel])))

(defn incoming?
  [edge node-id]
  (= (.-target edge) node-id))
  ;(= (.-id (.-targetNode edge)) node-id))

(defn outgoing?
  [edge node-id]
  (= (.-source edge) node-id))
  ;(= (.-id (.-sourceNode edge)) node-id))

(defn get-connections
  [edges node-id]
  (reduce (fn [acc edge]
            (cond
              (incoming? edge node-id)
              (update acc :incoming #(conj % (->clj edge)))
              (outgoing? edge (->js node-id))
              (update acc :outgoing #(conj % (->clj edge)))
              :else acc))
          {:incoming []
           :outgoing []}
          edges))

(reg-sub
 ::connections
 (fn [{:keys [edges]} [node-id]]
   (get-connections edges node-id)))

(reg-sub
 ::node-types
 (fn [db _query-vector]
   (select-keys db [:node-types])))
