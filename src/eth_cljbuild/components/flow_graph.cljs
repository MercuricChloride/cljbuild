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
       :onNodesChange #(re-frame.core/dispatch [:change-nodes %])
       :onEdgesChange #(re-frame.core/dispatch [:change-edges %])
       :onConnect #(re-frame.core/dispatch [:create-edge %])}
      [BG]]]))
