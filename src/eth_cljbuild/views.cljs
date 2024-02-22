(ns eth-cljbuild.views
 (:require
  [eth-cljbuild.components.ant-wrappers :refer [Button Collapse ConfigProvider
                                                Content Flex Header Layout]]
  [eth-cljbuild.components.flow-graph :refer [flow-component]]
  [eth-cljbuild.subs :as subs]
  [eth-cljbuild.utils :refer [->js]]
  [re-frame.core :refer [dispatch subscribe]]
  [reagent.core :as r]))

(defn editor-item
 [node-id k v]
 (let [text-ref (atom nil)
       handleChange (fn [e]
                      (let [value (.-value (.-target e))]
                        (reset! text-ref value)))
       item-string (str (key->js k))]
      [:div
       [:textarea {:onChange handleChange
                   :cols 80
                   :rows 10
                   :defaultValue v}]
       [Button {:onClick #(dispatch [:change-property node-id k @text-ref])} (str "Update " item-string)]]))

;; (def editor-item-component
;;   (r/reactify-component editor-item))


(defn editor-panel
 [showing? node-id properties]
 [:div.editor-container
  {:style {:display (if showing? "block" "none")}}
  [:div.editor-header
   [:h1 (str "Editor for node: " node-id)]]
  [Collapse
   {"items" (map-indexed (fn [i [key value]] {"key" i
                                              "label" key
                                              "children" [(r/as-element [editor-item node-id key value])]}) properties)}]])
   

(def theme {"algorithm" "dark"})

(defn main-panel
 []
 (let [{:keys [node-id showing? properties]} @(subscribe [::subs/editor-panel-state])]
   [^{"theme" theme} ConfigProvider
    [Flex
     {:style {:height "100vh"
              :width "100vw"}}
     [Layout
      [Header]
      [Content
       {:style {:height "100%"
                :width "100%"}}
       [editor-panel showing? node-id properties]
       [flow-component]]]]]))
