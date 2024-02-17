(ns eth-cljbuild.events
  (:require
   ["reactflow" :refer [addEdge applyEdgeChanges applyNodeChanges]]
   [eth-cljbuild.db :as db]
   [eth-cljbuild.utils :refer [->clj ->js find-in]]
   [re-frame.core :refer [reg-event-db reg-event-fx]]))
   

(reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(reg-event-fx
 :spawn-node
 (fn [{:keys [db]} [_ x y]]
   (let [nodes (:nodes db)
         id (str (count nodes))
         new-node {:id id
                   :data {:label id}
                   :position {:x x
                              :y y}}]
     {:db (assoc db :nodes (cons new-node nodes))})))

(defn delete-node
  [nodes node-id]
  (->js (filter (fn [node]
                    (let [node (->clj node)]
                     (not= (:id node) node-id)))
                nodes)))

(defn copy-node
  [nodes node-id]
  (let [node (find-in (fn [node]
                       (let [node (->clj node)]
                         (not= (:id node) node-id)))
                   nodes)]
    (->js (conj (->clj nodes) (assoc (->clj node) :id (str (random-uuid)))))))

(reg-event-fx
 :delete-node
 (fn [{:keys [db]} [_ node-id]]
   {:db (assoc db :nodes (delete-node (:nodes db) node-id))}))

(reg-event-fx
 :copy-node
 (fn [{:keys [db]} [_ node-id]]
   {:db (assoc db :nodes (copy-node (:nodes db) node-id))}))

(reg-event-fx
 :change-nodes
 (fn [{:keys [db]} [_ changes]]
     {:db (assoc db :nodes (applyNodeChanges (clj->js changes) (clj->js (:nodes db))))}))

(reg-event-fx
 :change-edges
 (fn [{:keys [db]} [_ changes]]
   {:db (assoc db :edges (applyEdgeChanges (->js changes) (->js (:edges db)))
                  :edge-changes changes)}))

(reg-event-fx
 :create-edge
 (fn [{:keys [db]} [_ params]]
   {:db (assoc db :edges (addEdge (->js params) (->js (:edges db))))}))

(reg-event-fx
 :show-context-menu
 (fn [{:keys [db]} [_ event node]]
     (let [{:keys [clientX clientY]} event
           {:keys [id data]} node]
       {:db (assoc db :context-menu {:showing true
                                      :node-id id
                                      :x clientX
                                      :y clientY
                                      :properties (keys data)})})))
(reg-event-fx
 :hide-context-menu
 (fn [{:keys [db]} _]
   {:db (assoc db :context-menu {:showing false
                                 :node-id 0
                                 :x 0
                                 :y 0
                                 :properties []})}))
