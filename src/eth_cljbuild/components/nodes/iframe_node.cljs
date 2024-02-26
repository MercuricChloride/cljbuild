(ns eth-cljbuild.components.nodes.iframe-node
  (:require
   [eth-cljbuild.components.nodes.base-node :refer [base-node]]
   [eth-cljbuild.subs :as subs]
   [re-frame.core :refer [subscribe]])
  (:require-macros
   [eth-cljbuild.macros :refer [def-element]]))

;; (defn
;;   iframe-node
;;   [{:keys [id data]}]
;;   (let [{:keys [label input-count output-count]} data
;;         !iframe (atom nil)
;;         connected-nodes @(subscribe [::subs/connections id])]
;;     [base-node
;;      id
;;      label
;;      [:<>
;;        ;;[Button {:onClick #(send-message @!iframe)} "send-message"]
;;        [:iframe {:srcDoc (format-html data)
;;                  :style {:width "100%"
;;                          :height "100%"}
;;                  :ref (fn [el] (reset! !iframe el))}]]]))
     ;; (map #(r/as-element [input :Left (str %)]) (range input-count))
     ;; (map #(r/as-element [output :Right (str %)]) (range output-count))]))
