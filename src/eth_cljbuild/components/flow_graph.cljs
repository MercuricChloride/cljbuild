(ns eth-cljbuild.components.flow-graph
  (:require
   ["reactflow" :refer [Background Panel] :default react-flow]
   [eth-cljbuild.components.ant-wrappers :refer [Button Collapse]]
   [eth-cljbuild.components.nodes.iframe-node :refer [IFrameNode]]
   [eth-cljbuild.components.nodes.math.AddNode :refer [AddNode NumberNode]]
   [eth-cljbuild.subs :as subs]
   [re-frame.core :as re-frame :refer [dispatch subscribe]]
   [reagent.core :as reagent]
   [reagent.core :as r]))

(defonce ReactFlow (reagent/adapt-react-class react-flow))
(defonce RFBackground (reagent/adapt-react-class Background))
(defonce RFPanel (reagent/adapt-react-class Panel))
(defonce nodeTypes #js{"adder" (r/reactify-component AddNode)
                       "iframe" (r/reactify-component IFrameNode)
                       "number" (r/reactify-component NumberNode)})

;; (defonce nodeTypes (clj->js {:adder AddNode
;;                              :iframe IFrameNode}))

(defn ControlPanel
  []
  (let [instance @(subscribe [::subs/rf-instance])
        set-viewport #(.setViewport instance %)]
    [RFPanel
     [Button {:onClick #(dispatch [:set-graph-state instance])} "Save State"]
     [Button {:onClick #(dispatch [:load-graph-state set-viewport])} "Load State"]]))

(defn ContextMenu
  []
  (let [{:keys [showing? x y properties node-id]} @(subscribe [::subs/context-menu-state])]
    (when showing?
      [:div.context-menu
       {:style {:top y
                :left x
                :z-index 1000}}
       (str "Node: " node-id)
       [Collapse
        {:items (map-indexed (fn [index property] {:key index
                                                   :label property
                                                   :children [:input property]}) properties)}]])))

(defn flow-component
  []
  (let [{:keys [nodes edges graph-state]} @(subscribe [::subs/graph-data])]
    [ReactFlow
     {:nodes nodes
      :edges edges
      :nodeTypes nodeTypes
      :onPaneClick #(dispatch [:hide-context-menu %])
      :onNodeContextMenu (fn [event node]
                             (dispatch [:edit-node (.-id node)]))
      :onNodesChange #(dispatch [:change-nodes %])
      :onEdgesChange #(dispatch [:change-edges %])
      :onConnect #(dispatch [:create-edge %])
      :onContextMenu (fn [e] (.preventDefault e))
      :onInit #(dispatch [:set-rf-instance %])}
     [RFBackground
      {:color "#000000"}]
       ;:variant "lines"}]
     ;;[ControlPanel]
     [ContextMenu]]))
