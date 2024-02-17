(ns eth-cljbuild.components.nodes.math.AddNode
  (:require
   [eth-cljbuild.components.flow-wrappers :refer [input output]]
   [reagent.core :as reagent])
  (:require-macros
   [eth-cljbuild.macros :refer [def-element]]))

(defn BaseNode
  [title [& inputs] [& outputs]]
  [:div.base-node
    [:label title]
    [:div.node-inputs inputs]
    [:div.base-node-ouputs outputs]])

(def-element
  AddNode
  [{:keys [id data]}]
  [BaseNode "AddNode" [[input {:key "a"} "a"]
                       [input {:key "b"} "b"]]
                      [[output {:key "a"} "a"]]])
