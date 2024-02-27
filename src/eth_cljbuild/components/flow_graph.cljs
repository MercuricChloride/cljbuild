(ns eth-cljbuild.components.flow-graph
  (:require
   [eth-cljbuild.api.graph :as graph]
   [eth-cljbuild.api.settings :as settings]
   [eth-cljbuild.components.flow-wrappers :refer [background react-flow]]
   [eth-cljbuild.components.nodes.math.add-node :refer [add-node]]
   [eth-cljbuild.components.nodes.math.number-node :refer [number-node]]
   [re-frame.core :as re-frame :refer [dispatch subscribe]]
   [reagent.core :as r]))

;; (defn ControlPanel
;;   []
;;   (let [instance @(subscribe [::subs/rf-instance])
;;         set-viewport #(.setViewport instance %)]
;;     [panel
;;      [button {:onClick #(dispatch [:set-graph-state instance])} "Save State"]
;;      [button {:onClick #(dispatch [:load-graph-state set-viewport])} "Load State"]]))

(defonce node-types #js{"adder" (r/reactify-component add-node)
                        "number" (r/reactify-component number-node)})

(defn flow-component
  []
  (let [{:keys [nodes edges]} @(subscribe [::graph/js-graph-data])
        {:keys [theme grid-style grid-color]} @(subscribe [::settings/theme])]
    [react-flow
     {:nodes nodes
      :edges edges
      :nodeTypes node-types
      :onPaneClick #(dispatch [:hide-context-menu %])
      :onNodeContextMenu (fn [event node]
                             (dispatch [:edit-node (.-id node)]))
      :onNodesChange #(dispatch [::graph/js-node-changes %])
      :onEdgesChange #(dispatch [:change-edges %])
      :onConnect #(dispatch [:create-edge %])
      :onContextMenu (fn [e] (.preventDefault e))
      :onInit #(dispatch [:set-rf-instance %])}
     [background
      {:color grid-color
       :variant grid-style}]]))
     ;;[ControlPanel]
