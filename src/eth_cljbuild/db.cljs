(ns eth-cljbuild.db
  (:require [eth-cljbuild.components.nodes.math.AddNode :refer [AddNode]]))

(defn -node
  ([label]
   {:id label
    :data {:label label}
    :type "adder"
    :position {:x 0 :y 0}})
  ([label x y]
   {:id label
    :data {:label label}
    :type "adder"
    :position {:x x :y y}}))

(defonce nodes [(-node "1")
                (-node "2" 100 100)])

(defonce edges [{:id "1-2"
                 :source "1"
                 :target "2"}])

(defonce node-types
  (clj->js {:adder AddNode}))

(defonce default-db
  {:nodes nodes
   :edges edges
   :node-types node-types})
