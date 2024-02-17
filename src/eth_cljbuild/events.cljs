(ns eth-cljbuild.events
  (:require
   [re-frame.core :refer [reg-event-fx reg-event-db]]
   [eth-cljbuild.db :as db]
   ["reactflow" :refer [applyNodeChanges applyEdgeChanges addEdge]]))
   

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
  (clj->js (filter (fn [node]
                       (let [node (js->clj node :keywordize-keys true)]
                         (not= (:id node) node-id)))
                   nodes)))

(defmulti node-toolbar-action
  (fn [db [action node-id]] action))

(defmethod node-toolbar-action :delete
  [db [_ node-id]]
  {:db (assoc db :nodes (delete-node (:nodes db) node-id))})

;; (defmethod node-toolbar-action :copy
;;   [cofx [_action node-id]]
;;   (fn [{:keys [db]} [_ action node-id]]
;;     {:db (assoc db :nodes (remove #(= (:id %) node-id) (:nodes db)))}))

(reg-event-fx
 :delete-node
 (fn [{:keys [db]} [_ node-id]]
   {:db (assoc db :nodes (delete-node (:nodes db) node-id))}))


(reg-event-fx
 :change-nodes
 (fn [{:keys [db]} [_ changes]]
     {:db (assoc db :nodes (applyNodeChanges (clj->js changes) (clj->js (:nodes db))))}))

(reg-event-fx
 :change-edges
 (fn [{:keys [db]} [_ changes]]
   {:db (assoc db :edges (applyEdgeChanges (clj->js changes) (clj->js (:edges db)))
                  :edge-changes changes)}))

(reg-event-fx
 :create-edge
 (fn [{:keys [db]} [_ params]]
   {:db (assoc db :edges (addEdge (clj->js params) (clj->js (:edges db))))}))

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
