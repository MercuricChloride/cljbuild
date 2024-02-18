(ns eth-cljbuild.views
 (:require
  [eth-cljbuild.components.flow-graph :refer [flow-component]]
  [eth-cljbuild.subs :as subs]
  [re-frame.core :refer [dispatch subscribe]]))

(defn editor-item
 [node-id item]
 (let [handleChange (fn [e]
                      (let [value (.-value (.-target e))]
                        (dispatch [:change-property node-id item value])))
       item-string (str item)]
      [:div.editor-item
        [:span
          [:p "Property: " item-string]
          [:input {:type "text" :onChange handleChange}]]]))
                   

(defn editor-panel
 [node-id properties]
 [:div.editor-container
   [:div.editor-header]
   [:h1 "Editor"]
   [:div.editor-content]
   [:ul.editor-list
    (map (fn [p] [editor-item node-id p]) properties)]])

(defn main-panel
 []
 (let [{:keys [node-id showing? properties]} @(subscribe [::subs/editor-panel-state])]
   [:div.main-panel
    (when showing?
      [editor-panel node-id properties])
    [flow-component]]))
