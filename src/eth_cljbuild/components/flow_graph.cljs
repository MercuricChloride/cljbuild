(ns eth-cljbuild.components.flow-graph
  (:require
   ["reactflow" :refer [Background] :default react-flow]
   [eth-cljbuild.components.nodes.math.AddNode :refer [AddNode IFrameNode]]
   [eth-cljbuild.subs :as subs]
   [re-frame.core :as re-frame]
   [reagent.core :as reagent]))

(defonce ReactFlow (reagent/adapt-react-class react-flow))
(defonce BG (reagent/adapt-react-class Background))
(defonce nodeTypes (clj->js {:adder AddNode
                             :iframe IFrameNode}))

(defn ContextMenu
  []
  (let [{:keys [showing x y properties node-id]} @(re-frame/subscribe [::subs/context-menu-state])]
    (if showing
      [:div
       {:style {:position "absolute"
                :top y
                :left x
                :z-index 1000}}
       (str "Node: " node-id)
       [:ul
        (map (fn [property] [:li property]) properties)]])))

    

(defn onNodeContextMenu
  [event node]
  (js/console.log "Node context menu" event node))

(defn flow-component
  []
  (let [{:keys [nodes edges]} @(re-frame/subscribe [::subs/graph-data])]
    [:div
     {:style {:width "100vw"
              :height "100vh"}}
     [ReactFlow
      {:nodes nodes
       :edges edges
       :nodeTypes nodeTypes
       :onPaneClick #(re-frame.core/dispatch [:hide-context-menu %])
       :onNodeContextMenu #(re-frame.core/dispatch [:show-context-menu % %])
       :onNodesChange #(re-frame.core/dispatch [:change-nodes %])
       :onEdgesChange #(re-frame.core/dispatch [:change-edges %])
       :onConnect #(re-frame.core/dispatch [:create-edge %])
       :onContextMenu (fn [e] (.preventDefault e))}
      [BG]
      [ContextMenu]]]))
