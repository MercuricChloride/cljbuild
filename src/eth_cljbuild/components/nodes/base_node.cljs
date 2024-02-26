(ns eth-cljbuild.components.nodes.base-node
  (:require
   [eth-cljbuild.components.ant-wrappers :refer [button card flex]]
   [eth-cljbuild.components.flow-wrappers :refer [input node-toolbar output
                                                  resizer]]
   [re-frame.core :refer [dispatch]]
   [reagent.core :as r]))

(defn node-interactions
  [id]
  [node-toolbar
   [flex
    {:gap "small"}
    [button {:onClick #(dispatch [:remove-node id])} "delete"]
    [button {:onClick #(dispatch [:copy-node id])} "copy"]
    [button {:onClick #(dispatch [:edit-node id])} "edit"]
    [button {:onClick #(dispatch [:save-node-type id])} "save node type"]]])

(defn node-inputs
  [input-count]
  [flex
   {:vertical true
    :justify "space-between"}
   (map #(r/as-element [input :Left (str %)]) (range input-count))])

(defn node-outputs
  [output-count]
  [flex
   {:vertical true
    :justify "center"
    :align "center"}
   (map #(r/as-element [output :Right (str %)]) (range output-count))])
    
(defn base-node
  ([id data]
   (base-node id "" data))
  ([id body {:keys [label input-count output-count]}]
   [card
    {:style {:borderRadius "5px"}
     :title label}
    [:div
      {:style {:minWidth "150px"}}
      [node-interactions id]
      [resizer {:height "500px" :width "500px"}]
      [flex
        {:justify "space-between"
          :width "100%"}
        [node-inputs input-count]
        body
        [node-outputs output-count]]]]))
