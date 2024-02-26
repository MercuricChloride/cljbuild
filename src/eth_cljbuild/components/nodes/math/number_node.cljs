(ns eth-cljbuild.components.nodes.math.number-node
  (:require
   [eth-cljbuild.components.nodes.base-node :refer [base-node]]
   [eth-cljbuild.utils :refer [->clj]]
   [re-frame.core :refer [dispatch]]))

(defn
  number-node
  [{:keys [id data]}]
  (let [data (->clj data)]
   [base-node
    id
    [:input {:onBlur #(dispatch [:update-output-value id "0" (.. % -target -value)])}]
    data]))
