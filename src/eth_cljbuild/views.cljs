(ns eth-cljbuild.views
 (:require
  [eth-cljbuild.components.flow-graph :refer [flow-component]]
  [eth-cljbuild.subs :as subs]
  [re-frame.core :refer [dispatch subscribe]]))

(defn editor-item
 [node-id k v]
 (let [text-ref (atom nil)
       handleChange (fn [e]
                      (let [value (.-value (.-target e))]
                        (reset! text-ref value)))
       item-string (str (key->js k))]
      [:div.editor-item
        [:span
          [:p "Property: " item-string]
          [:textarea {:onChange handleChange}
           v]]
        [:button {:onClick #(dispatch [:change-property node-id k @text-ref])} "Update"]]))
                   

(defn editor-panel
 [node-id properties]
 [:div.editor-container
   [:div.editor-header]
   [:h1 "Editor"]
   [:div.editor-content]
   [:ul.editor-list
    (map-indexed (fn [i [key value]] ^{:key i} [editor-item node-id key value]) properties)]])

(defn main-panel
 []
 (let [{:keys [node-id showing? properties]} @(subscribe [::subs/editor-panel-state])]
   [:div.main-panel
    (when showing?
      [editor-panel node-id properties])
    [flow-component]]))
