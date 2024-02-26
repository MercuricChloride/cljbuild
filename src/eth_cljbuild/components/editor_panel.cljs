(ns eth-cljbuild.components.editor-panel
  (:require
   [eth-cljbuild.components.ant-wrappers :refer [button collapse flex text-area]]
   [eth-cljbuild.components.clojure-editor :refer [Editor]]
   [eth-cljbuild.utils :refer [->js]]
   [re-frame.core :refer [dispatch]]
   [reagent.core :as r]))

(defn editor-item
 [node-id k v]
 (let [text-ref (atom nil)
       js-value (->js v)
       is-object? (object? js-value)
       handleChange (fn [e]
                      (let [value (.-value (.-target e))]
                        (if is-object?
                          (reset! text-ref (.parse js/JSON value))
                          (reset! text-ref value))))
       item-string (str (key->js k))]
      [flex
       {:vertical true
        :width "50%"}
       (if (= k :cljs)
         [Editor]
         [text-area {:onChange handleChange
                     :cols 80
                     :rows 10
                     :defaultValue (if is-object?
                                       (.stringify js/JSON js-value)
                                       v)}])
       [button {:onClick #(dispatch [:change-property node-id k @text-ref])} (str "Update " item-string)]]))

(defn editor-panel
 [showing? node-id properties]
 [:div.editor-container
  {:style {:display (if showing? "block" "none")}}
  [:div.editor-header>h1 (str "Editor for node: " node-id)]
  [collapse
   {"items" (map-indexed
             (fn
               [i [key value]]
               {"key" i
                "label" key
                "children" (r/as-element [editor-item node-id key value])})
             properties)}]])
