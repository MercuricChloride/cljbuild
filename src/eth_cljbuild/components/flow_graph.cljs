(ns eth-cljbuild.components.flow-graph
  (:require
   ["reactflow" :refer [Background] :default react-flow]
   [re-frame.core :as re-frame]
   [reagent.core :as reagent]))

(def ReactFlow (reagent/adapt-react-class react-flow))
(def BG (reagent/adapt-react-class Background))

(defn flow-component
  [nodes edges]
  [:div
   {:style {:width "100%"
            :height 500}}
   [ReactFlow
    {:nodes nodes
     :edges edges
     :onNodesChange #(re-frame.core/dispatch [:change-nodes %])
     :onEdgesChange #(re-frame.core/dispatch [:change-edges %])
     :onConnect #(re-frame.core/dispatch [:create-edge %])}
    [BG]]])
