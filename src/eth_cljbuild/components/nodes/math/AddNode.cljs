(ns eth-cljbuild.components.nodes.math.AddNode
  (:require
   [eth-cljbuild.components.flow-wrappers :refer [input output resizer]])
  (:require-macros
   [eth-cljbuild.macros :refer [def-element]]))

(defn BaseNode
  ([title inputs outputs]
   (BaseNode title "" inputs outputs))
  ([title body inputs outputs]
   [:div.base-node
     [resizer {:height "500px" :width "500px"}]
     title
     body
     [:div.node-inputs (map-indexed #(with-meta %2 {:key %1}) inputs)]
     [:div.base-node-ouputs (map-indexed #(with-meta %2 {:key %1}) outputs)]]))

(def-element
  AddNode
  {:keys [id data]}
  [BaseNode "AddNode" [[input :Left "a"]
                       [input :Left "b"]]
                      [[output :Right "a"]]])
(def-element
  IFrameNode
  {:keys [id data]}
  (let [{:keys [html label]} data]
    [BaseNode
     label
     [:iframe {:srcDoc html
               :style {:width "100%"
                       :height "100%"}}]

     []
     []]))
