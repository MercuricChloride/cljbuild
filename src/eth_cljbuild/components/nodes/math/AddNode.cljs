(ns eth-cljbuild.components.nodes.math.AddNode
  (:require
   [eth-cljbuild.components.ant-wrappers :refer [Button Card Flex]]
   [eth-cljbuild.components.flow-wrappers :refer [input node-toolbar output
                                                  resizer]]
   [eth-cljbuild.subs :as subs]
   [eth-cljbuild.utils :refer [->clj ->js]]
   [eth-cljbuild.components.nodes.base-node :refer [BaseNode]]
   [re-frame.core :refer [dispatch subscribe]]
   [reagent.core :as r]))

(add-tap #(js/console.log "TAP: " (->js %)))

(defn
  NumberNode
  [{:keys [id data]}]
  (let [data (->clj data)]
   [BaseNode
    id
    [:input {:onBlur #(dispatch [:update-output-value id "0" (.. % -target -value)])
             :initalValue "asdflkj"}]
    data]))

(defn
  AddNode
  [{:keys [id data]}]
  (let [data (->clj data)
        input-values @(subscribe [::subs/connections id])]
   (js/console.log "INPUT VALUES" (->js input-values))
   [BaseNode
    id
    [:div
     [Button {:onClick #(dispatch [:change-property id :label "UPDATED"])} "UPDATE"]]
    (->clj data)]))
