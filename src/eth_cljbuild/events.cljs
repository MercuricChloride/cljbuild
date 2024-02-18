(ns eth-cljbuild.events
  (:require
   ["reactflow" :refer [addEdge applyEdgeChanges applyNodeChanges]]
   [eth-cljbuild.db :as db]
   [eth-cljbuild.event-helpers.node-changes :refer [remove-node create-node]]
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
  (let [node (find-in #(= (:id (->clj %)) node-id) nodes)
        node (assoc (->clj node) :id (str (random-uuid))
                                 :x (+ 20 (:x node))
                                 :y (+ 20 (:y node)))]
      node))

(reg-event-fx
 :remove-node
 (fn [_ [_ node-id]]
     {:dispatch [:change-nodes [(remove-node node-id)]]}))

(reg-event-fx
 :copy-node
 (fn [{:keys [db]} [_ node-id]]
     (let [copied-node (copy-node (:nodes db) node-id)]
          {:dispatch [:change-nodes [(create-node copied-node)]]})))

(reg-event-fx
 :change-property
 (fn [{:keys [db]} [_ node-id property value]]
     (let [nodes (:nodes db)
           new-nodes (map (fn [node]
                            (if (= (.-id node) node-id)
                              (let [node (->clj node)
                                    node-data (:data node)]
                                (->js (assoc node
                                             :data (->js (assoc node-data
                                                               property value)))))
                              node))
                          nodes)]
          {:db (assoc db :nodes (->js new-nodes))})))

(reg-event-fx
 :change-nodes
 (fn [{:keys [db]} [_ changes]]
     {:db (assoc db :nodes (applyNodeChanges (->js changes) (->js (:nodes db))))}))

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
 :edit-node
 (fn [{:keys [db]} [_ node-id]]
   (let [properties (->clj (.-data (find-in #(= (.-id  %) node-id) (:nodes db))))]
     {:db (assoc db :editor-panel {:showing? true
                                   :node-id node-id
                                   :properties properties})})))

(reg-event-fx
 :show-context-menu
 (fn [{:keys [db]} [_ event node]]
     (let [{:keys [clientX clientY]} event
           {:keys [id data]} node]
       {:db (assoc db :context-menu {:showing true
                                      :node-id id
                                      :x clientX
                                      :y clientY
                                      :properties data})})))
(reg-event-fx
 :hide-context-menu
 (fn [{:keys [db]} _]
   {:db (assoc db :context-menu {:showing false
                                 :node-id 0
                                 :x 0
                                 :y 0
                                 :properties []}
                   :editor-panel {:showing false
                                  :node-id 0
                                  :properties []})}))
