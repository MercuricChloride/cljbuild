(ns eth-cljbuild.events
  (:require
   [re-frame.core :as re-frame]
   [eth-cljbuild.db :as db]
   ["reactflow" :refer [applyNodeChanges applyEdgeChanges addEdge]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-fx
 :spawn-node
 (fn [{:keys [db]} [_ x y]]
   (let [nodes (:nodes db)
         id (str (count nodes))
         new-node {:id id
                   :data {:label id}
                   :position {:x x
                              :y y}}]
     {:db (assoc db :nodes (cons new-node nodes))})))


(re-frame/reg-event-fx
 :change-nodes
 (fn [{:keys [db]} [_ changes]]
     {:db (assoc db :nodes (applyNodeChanges (clj->js changes) (clj->js (:nodes db))))}))

(re-frame/reg-event-fx
 :change-edges
 (fn [{:keys [db]} [_ changes]]
   {:db (assoc db :edges (applyEdgeChanges (clj->js changes) (clj->js (:edges db))))}))

(re-frame/reg-event-fx
 :create-edge
 (fn [{:keys [db]} [_ params]]
   {:db (assoc db :edges (addEdge (clj->js params) (clj->js (:edges db))))}))
