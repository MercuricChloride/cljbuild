(ns eth-cljbuild.events
  (:require
   ["reactflow" :refer [addEdge applyEdgeChanges applyNodeChanges]]
   [eth-cljbuild.db :as db]
   [eth-cljbuild.event-helpers.node-changes :refer [remove-node create-node]]
   [eth-cljbuild.utils :refer [->clj ->js find-in]]
   [re-frame.core :refer [reg-event-db reg-event-fx]]))

(defonce save-key "eth-cljbuild-graph-state")
(defonce node-types-key "eth-cljbuild-node-types")

(defn get-node-types
  "Retrieves the node types from local storage if they exist, otherwise returns an empty map"
  []
  (if-some [node-types (.getItem js/localStorage node-types-key)]
    (->clj (.parse js/JSON node-types))
    {}))

(defn save-node-type
  "Saves the node's data to local storage"
  [node-name node-data]
  (let [node-types (get-node-types)
        exists? (some? ((keyword node-name) node-types))]
    (if exists?
      (js/alert "THIS KEY ALREADY EXISTS! Choose a new name!")
      (.setItem js/localStorage node-types-key (.stringify js/JSON (->js (assoc node-types node-name node-data)))))))

(reg-event-fx
 :save-node-type
 (fn [{:keys [db]} [_ id]]
   (let [node (find-in #(= (:id %) id) (->clj (:nodes db)))
         node-name (js/prompt "Enter a name for this node type")]
     (save-node-type node-name (:data node)))))

;; EVENTS FOR SAVING AND LOADING GRAPH STATE

(reg-event-fx
 :set-rf-instance
 (fn [{:keys [db]} [_ instance]]
   {:db (assoc db :rf-instance instance)}))

(reg-event-fx
 :set-graph-state
 (fn [{:keys [db]} [_ instance-value]]
     (let [state-json (.stringify js/JSON (.toObject instance-value))
           key save-key]
      (.setItem js/localStorage key state-json))))

(reg-event-fx
 :load-graph-state
 (fn [{:keys [db]} [_ set-viewport]]
   (let [{:keys [nodes edges viewport]} (->clj (.parse js/JSON (.getItem js/localStorage save-key)))]
        (set-viewport viewport)
        {:db (assoc db :nodes nodes :edges edges)})))

;; EVENTS FOR CREATING AND MANAGING NODES AND THEIR PROPERTIES

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
                              (let [node-data (.-data node)]
                                (set! (.-data node)
                                      (->js (assoc (->clj node-data)
                                                   property value)))
                                node)
                              node))
                          nodes)]
          {:db (assoc db :nodes (->js new-nodes))})))

(reg-event-fx
 :update-output-value
 (fn [{:keys [db]} [_ node-id handle-id value]]
     (let [nodes (->clj (:nodes db))
           new-nodes (map (fn [node]
                            (if (= (:id node) node-id)
                              (let [assoc-value (assoc-in node
                                                 [:data :output-map (str handle-id)]
                                                 value)]
                                assoc-value)
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
       {:db (assoc db :context-menu {:showing? true
                                      :node-id id
                                      :x clientX
                                      :y clientY
                                      :properties data})})))

(reg-event-fx
 :hide-context-menu
 (fn [{:keys [db]} _]
   {:db (assoc db :context-menu {:showing? false
                                 :node-id 0
                                 :x 0
                                 :y 0
                                 :properties []}
                  :editor-panel (assoc (:editor-panel db) :showing? false))}))
