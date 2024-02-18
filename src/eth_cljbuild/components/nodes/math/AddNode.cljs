(ns eth-cljbuild.components.nodes.math.AddNode
  (:require
   [eth-cljbuild.components.flow-wrappers :refer [input node-toolbar output
                                                  resizer]]
   [eth-cljbuild.event-helpers.node-changes :refer [remove-node]]
   [re-frame.core :refer [dispatch]])
  (:require-macros
   [eth-cljbuild.macros :refer [def-element]]))

(defn BaseNode
  ([id title inputs outputs]
   (BaseNode id title "" inputs outputs))
  ([id title body inputs outputs]
   [:div.base-node
     [node-toolbar
      [:button {:onClick #(dispatch [:remove-node id])} "delete"]
      [:button {:onClick #(dispatch [:copy-node id])} "copy"]
      [:button {:onClick #(dispatch [:edit-node id])} "edit"]]
     [resizer {:height "500px" :width "500px"}]
     title
     body
     [:div.node-inputs (map-indexed #(with-meta %2 {:key %1}) inputs)]
     [:div.base-node-ouputs (map-indexed #(with-meta %2 {:key %1}) outputs)]]))

(def-element
  AddNode
  {:keys [id data]}
  [BaseNode id (:label data)  [[input :Left "a"]
                               [input :Left "b"]]
                              [[output :Right "a"]]])
(def-element
  IFrameNode
  {:keys [id data]}
  (let [{:keys [html label]} data
        !iframe (atom nil)]
    [BaseNode
     id
     label
     [:iframe {:srcDoc html
               :style {:width "100%"
                       :height "100%"}
               :ref (fn [el] (reset! !iframe el))}]
     [[input :Left "a"]]
     [[output :Right "a"]]]))
