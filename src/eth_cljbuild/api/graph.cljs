(ns eth-cljbuild.api.graph
  (:require
   ["reactflow" :refer [addEdge applyEdgeChanges applyNodeChanges]]
   [eth-cljbuild.utils :refer [->clj ->js find-in]]
   [re-frame.core :refer [reg-event-db reg-sub]]))

;; NOTE It's important to note that
;; the convention in the codebase
;; is that all items in the db should
;; be stored as clojure objects, not
;; as JS or JSON data. It just makes
;; things simpler

;; ============================
;; HELPERS
;; ============================

(defn incoming?
  "Checks if `edge` is incoming to the `node-id`"
  [edge node-id]
  (= (.-target edge) node-id))

(defn outgoing?
  "Checks if `edge` is outgoing from the `node-id`"
  [edge node-id]
  (= (.-source edge) node-id))

(defn get-connections
  "Gets all incoming and outgoing edges for `node-id`"
  [edges node-id]
  (reduce (fn [acc edge]
            (cond
              (incoming? edge node-id) (update acc :incoming #(conj % (->clj edge)))
              (outgoing? edge node-id) (update acc :outgoing #(conj % (->clj edge)))
              :else acc))
          {:incoming []
           :outgoing []}
          edges))

(defn get-output-value
  "Finds a node matching `node-id` and returns the value of the output port `handle-id`"
  [nodes node-id handle-id]
  (let [node (->clj (find-in #(= (.-id %) node-id) nodes))
        output-map (get-in node [:data :output-map (keyword handle-id)])]
    output-map))


;; ============================
;; SUBS
;; ============================

;; TODO Make a sub that treats the nodes as a map from
;; id -> node for faster access

;; Returns the current state of the graph
(reg-sub
 ::js-graph-data
 (fn [db _query-vector]
   (let [{:keys [nodes edges]} (:graph db)]
     {:nodes (->js nodes)
      :edges (->js edges)})))


;; Returns a map containing the incoming and outgoing edges for a node
;; NOTE I think we really only care about the incoming values
(reg-sub
 ::connections
 (fn [{:keys [nodes edges]} [_ node-id]]
   (let [{:keys [incoming outgoing]} (get-connections edges node-id)]
     (println "INCOMING: " (->js incoming))
     (->> (->js incoming)
          (map #(get-output-value
                             nodes
                             (.-source %)
                             (.-sourceHandle %)))))))

;; ============================
;; EVENTS
;; ============================

(reg-event-db
 ::js-node-changes
 (fn [db [_ js-node-changes]]
     (let [js-nodes (->js (get-in db [:graph :nodes]))
           new-nodes (applyNodeChanges js-node-changes js-nodes)]
       (assoc-in db [:graph :nodes] (->clj new-nodes)))))
