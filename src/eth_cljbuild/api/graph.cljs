(ns eth-cljbuild.api.graph
  (:require
   [eth-cljbuild.utils :refer [->clj ->js find-in]]
   [re-frame.core :refer [reg-sub]]))

;; NOTE It's important to note that
;; the convention in the codebase
;; is that all items in the db should
;; be stored as clojure objects, not
;; as JS or JSON data. It just makes
;; things simpler

(reg-sub
 ::graph-data
 (fn [db _query-vector]
  (:graph db)))

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
