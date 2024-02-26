(ns eth-cljbuild.db
  (:require
   [eth-cljbuild.utils :refer [->clj]]))

(defn -node
  ([label]
   {:id label
    :data {:label label
           :input-count 2
           :output-count 1
           :output-map {}
           :cljs "(let [[a b] inputs] (+ a b))"}
    :type "adder"
    :position {:x 0 :y 0}})
  ([label x y]
   {:id label
    :data {:label label
           :input-count 2
           :output-count 1
           :output-map {}
           :cljs "(let [[a b] inputs] (+ a b))"}
    :type "adder"
    :position {:x x :y y}})
  ([label x y type]
   {:id label
    :data {:label label
           :cljs ""
           :input-count 2
           :output-count 1
           :output-map {}}
    :type type
    :position {:x x :y y}}))

(defn- number-node
  [id x y]
  {:id id
   :data {:label "number"
          :input-count 0
          :output-count 1
          :output-map {}}
   :type "number"
   :position {:x x :y y}})

(defonce initial-nodes [(number-node "1" 0 0)
                        (number-node "2" 0 0)
                        (-node "3" 100 100)])
(defonce initial-edges [])

(defonce node-types-key "eth-cljbuild-node-types")
(defn get-node-types
  "Retrieves the node types from local storage if they exist, otherwise returns an empty map"
  []
  (if-some [node-types (.getItem js/localStorage node-types-key)]
    (->clj (.parse js/JSON node-types))
    {}))

(defonce default-db
  {:settings {:visual {:theme "light"
                       :grid-style "lines"
                       :grid-color "#000000"}}
   :graph {:nodes initial-nodes
           :edges initial-edges}
   :globals {:rf-instance {}
             :node-types {}}
   :menus {:node-editor {:showing? false
                         :node-id 0
                         :properties []}}})
