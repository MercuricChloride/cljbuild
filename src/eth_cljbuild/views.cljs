(ns eth-cljbuild.views
  (:require
   [eth-cljbuild.components.flow-graph :refer [flow-component]]
   [eth-cljbuild.subs :as subs]
   [re-frame.core :as re-frame]))

(defn greeting
  [name count]
  (str "Hello from " name "! The button has been clicked: " count " times!"))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])
        count (re-frame/subscribe [::subs/count])
        nodes (re-frame/subscribe [::subs/nodes])
        edges (re-frame/subscribe [::subs/edges])]
    [:div
     [:h1
      (greeting @name @count)]
     [:button
      {:on-click #(re-frame.core/dispatch [:spawn-node (rand 500) (rand 500)])}
      "Increase count"]
     [flow-component @nodes @edges]]))
