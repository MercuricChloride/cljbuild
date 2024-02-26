(ns eth-cljbuild.components.nodes.math.add-node
  (:require
   [eth-cljbuild.components.nodes.base-node :refer [base-node]]
   [eth-cljbuild.sci-utils :refer [sci-eval]]
   [eth-cljbuild.utils :refer [->clj]]
   [eth-cljbuild.api.graph :as graph]
   [re-frame.core :refer [dispatch subscribe]]))

(defn
  add-node
  [{:keys [id data]}]
  (let [data (->clj data)
        {:keys [cljs output-map input-count]} data
        input-values @(subscribe [::graph/connections id])
        sci-output (sci-eval input-count input-values cljs)]
   (when (not= sci-output (:0 output-map))
     (dispatch [:update-output-value id "0" sci-output]))
   [base-node
    id
    (->clj data)]))
