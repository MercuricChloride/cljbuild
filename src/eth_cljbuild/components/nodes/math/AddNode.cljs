(ns eth-cljbuild.components.nodes.math.AddNode
  (:require
   [eth-cljbuild.components.ant-wrappers :refer [Button Card Flex]]
   [eth-cljbuild.components.flow-wrappers :refer [input node-toolbar output
                                                  resizer]]
   [eth-cljbuild.subs :as subs]
   [eth-cljbuild.utils :refer [->clj ->js]]
   [eth-cljbuild.components.nodes.base-node :refer [BaseNode]]
   [re-frame.core :refer [dispatch subscribe]]
   [reagent.core :as r]
   [sci.core :as sci]))

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

(defn sci-eval
  [inputs code]
  (let [sci-inputs (sci/new-var 'inputs inputs)]
    (sci/eval-string code {:namespaces {'user {'inputs sci-inputs}}})))

(defn
  AddNode
  [{:keys [id data]}]
  (let [data (->clj data)
        {:keys [cljs output-map]} data
        input-values @(subscribe [::subs/connections id])
        sci-output (sci-eval input-values cljs)]
   (when (not= sci-output (:0 output-map))
     (dispatch [:update-output-value id "0" sci-output]))
   ;;(dispatch [:update-output-value id "0" sci-output])
   (js/console.log "INPUT VALUES" (->js input-values))
   (js/console.log "Sci output" sci-output)
   [BaseNode
    id
    [:div
     [Button {:onClick #(dispatch [:change-property id :label "UPDATED"])} "UPDATE"]]
    (->clj data)]))
