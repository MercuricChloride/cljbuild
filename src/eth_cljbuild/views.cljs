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
        [:h2 item-string]
        [:textarea {:onChange handleChange
                    :cols 80
                    :rows 10
                    :defaultValue v}]
          
        [:button {:onClick #(dispatch [:change-property node-id k @text-ref])} (str "Update " item-string)]]))
                   

(defn editor-panel
 [node-id properties]
 [:div.editor-container
   [:div.editor-header
    [:h1 (str "Editor for node: " node-id)]]
   [:div.editor-content
    [:ul.editor-list
     (map-indexed (fn [i [key value]] ^{:key i} [editor-item node-id key value]) properties)]]])

(defn main-panel
 []
 (let [{:keys [node-id showing? properties]} @(subscribe [::subs/editor-panel-state])]
   [:div.main-panel
    (when showing?
      [editor-panel node-id properties])
    [flow-component]]))
