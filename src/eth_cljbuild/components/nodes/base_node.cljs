(ns eth-cljbuild.components.nodes.base-node
  (:require
   [eth-cljbuild.components.ant-wrappers :refer [Button Card Flex]]
   [eth-cljbuild.components.flow-wrappers :refer [input node-toolbar output
                                                  resizer]]
   [re-frame.core :refer [dispatch]]
   [reagent.core :as r]))

(defn NodeToolbar
  [id]
  [node-toolbar
   [Flex
    {:gap "small"}
    [Button {:onClick #(dispatch [:remove-node id])} "delete"]
    [Button {:onClick #(dispatch [:copy-node id])} "copy"]
    [Button {:onClick #(dispatch [:edit-node id])} "edit"]
    [Button {:onClick #(dispatch [:save-node-type id])} "save node type"]]])

(defn NodeInputs
  [input-count]
  [Flex
   {:vertical true
    :justify "space-between"}
   (map #(r/as-element [input :Left (str %)]) (range input-count))])

(defn NodeOutputs
  [output-count]
  [Flex
   {:vertical true
    :justify "center"
    :align "center"}
   (map #(r/as-element [output :Right (str %)]) (range output-count))])
    
(defn BaseNode
  ([id data]
   (BaseNode id "" data))
  ([id body {:keys [label input-count output-count]}]
   [Card
    {:style {:borderRadius "5px"}
     :title label}
    [:div
      {:style {:minWidth "150px"}}
      [NodeToolbar id]
      [resizer {:height "500px" :width "500px"}]
      [Flex
        {:justify "space-between"
          :width "100%"}
        [NodeInputs input-count]
        body
        [NodeOutputs output-count]]]]))
