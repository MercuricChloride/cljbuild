(ns eth-cljbuild.views
  (:require
   [eth-cljbuild.components.flow-graph :refer [flow-component]]))
   

(defn main-panel []
    [:div
     [flow-component]])
