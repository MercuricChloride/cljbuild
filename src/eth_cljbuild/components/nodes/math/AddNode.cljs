(ns eth-cljbuild.components.nodes.math.AddNode
  (:require
   [eth-cljbuild.components.flow-wrappers :refer [input output]]
   [reagent.core :as reagent])
  (:require-macros
   [eth-cljbuild.macros :refer [def-element]]))

(defn BaseNode
  [title inputs outputs]
  [:div.base-node
    [:label title]
    [:div.node-inputs (map-indexed #(with-meta %2 {:key %1}) inputs)]
    [:div.base-node-ouputs (map-indexed #(with-meta %2 {:key %1}) outputs)]])

(def-element
  AddNode
  {:keys [id data]}
  [BaseNode "AddNode" [[input :Left "a"]
                       [input :Left "b"]]
                      [[output :Right "a"]]])
(def-element
  IFrameNode
  {:keys [id data]}
  (let [html (get data :html)]
    [BaseNode [:iframe {:srcDoc html}]
              [[input :Left "a"]
               [input :Left "b"]]
              [[output :Right "a"]]]))
