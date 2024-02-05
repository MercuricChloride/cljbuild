(ns eth-cljbuild.views
  (:require
   ["reactflow" :default react-flow]
   [re-frame.core :as re-frame]
   [reagent.core :as reagent]
   [eth-cljbuild.subs :as subs]))

(def ReactFlow (reagent/adapt-react-class react-flow))

(defn flow-component
  []
  [:div
      {:style {:width 500
               :height 500}}
      [(reagent/adapt-react-class react-flow)]])

(defn greeting
  [name count]
  (str "Hello from " name "! The button has been clicked: " count " times!"))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])
        count (re-frame/subscribe [::subs/count])]
    [:div
     [:h1
      (greeting @name @count)]
     [:button
      {:on-click #(re-frame.core/dispatch [:update-count (inc @count)])}
      "Increase count"]
     [flow-component]
     ]))
