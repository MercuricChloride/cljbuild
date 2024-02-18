(ns eth-cljbuild.components.flow-graph
  (:require
   ["reactflow" :refer [Background Panel] :default react-flow]
   [eth-cljbuild.components.nodes.math.AddNode :refer [AddNode IFrameNode]]
   [eth-cljbuild.subs :as subs]
   [eth-cljbuild.utils :refer [->clj]]
   [re-frame.core :as re-frame :refer [dispatch subscribe]]
   [reagent.core :as reagent]))

(defonce ReactFlow (reagent/adapt-react-class react-flow))
(defonce RFBackground (reagent/adapt-react-class Background))
(defonce RFPanel (reagent/adapt-react-class Panel))
(defonce nodeTypes (clj->js {:adder AddNode
                             :iframe IFrameNode}))

(defn ContextMenu
  []
  (let [{:keys [showing x y properties node-id]} @(subscribe [::subs/context-menu-state])]
    (when showing
      [:div.context-menu
       {:style {:top y
                :left x
                :z-index 1000}}
       (str "Node: " node-id)
       [:ul
        (map-indexed (fn [index property] ^{:key index} [:li property]) properties)]])))

(defn flow-component
  []
  (let [{:keys [nodes edges]} @(subscribe [::subs/graph-data])]
    [ReactFlow
     {:nodes nodes
      :edges edges
      :nodeTypes nodeTypes
      :onPaneClick #(dispatch [:hide-context-menu %])
      :onNodeContextMenu (fn [event node]
                             (dispatch [:show-context-menu (->clj event) (->clj node)]))
      :onNodesChange #(dispatch [:change-nodes %])
      :onEdgesChange #(dispatch [:change-edges %])
      :onConnect #(dispatch [:create-edge %])
      :onContextMenu (fn [e] (.preventDefault e))}
     [RFBackground]
     [ContextMenu]]))
