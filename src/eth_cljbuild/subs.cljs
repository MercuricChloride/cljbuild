(ns eth-cljbuild.subs
  (:require
   [eth-cljbuild.utils :refer [->clj ->js find-in]]
   [re-frame.core :refer [reg-sub]]))

(reg-sub
 ::graph-data
 (fn [db _query-vector]
  (select-keys db [:nodes :edges])))

(reg-sub
 ::node-data
 (fn [db [_ node-id]]
   (->clj (get-in db [:nodes node-id]))))

(reg-sub
 ::rf-instance
 (fn [db _]
  (:rf-instance db)))

(reg-sub
 ::context-menu-state
 (fn [db _]
   (get-in db [:context-menu])))

(reg-sub
 ::editor-panel-state
 (fn [db _]
   (get-in db [:editor-panel])))

(defn incoming?
  [edge node-id]
  (= (.-target edge) node-id))

(defn outgoing?
  [edge node-id]
  (= (.-source edge) node-id))

(defn get-connections
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
          

   


;; (reg-sub
;;  ::input-values
;;  (fn [_ [_ node-id incoming-edges]]
;;      (let [input-edges (filter #())])))

;; Should return a map from input-index -> value connected to that port
;; (reg-sub
;;  ::input-values
;;  (fn [{:keys [nodes edges]} [node-id]]
;;    (let [connections (get-connections edges node-id)
;;          incoming-edges (-> connections :incoming)
;;          input-edges (filter #(= (.-target %) node-id) incoming-edges)
;;          input-values (map (fn [edge]
;;                              (let [source-node-id (.-source edge)
;;                                    source-node (get-in nodes [source-node-id :data])]
;;                                {:index (.-target-port edge)
;;                                 :value (get-in source-node [:output-map (.-source-port edge)])}))
;;                            input-edges)]
;;      input-values)))

(reg-sub
 ::node-types
 (fn [db _query-vector]
   (select-keys db [:node-types])))
