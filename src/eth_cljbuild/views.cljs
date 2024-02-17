(ns eth-cljbuild.views
 (:require
  [eth-cljbuild.components.flow-graph :refer [flow-component]]))

(defn editor-item
 [item]
 [:div.editor-item
  [:li item]])

(defn editor-panel
 [properties]
 (let [properties ()])
 [:div.editor-container
  [:div.editor-header
   [:h1 "Editor"]]
  [:div.editor-content
   [:ul.editor-list
    (map editor-item properties)]]])

(defn main-panel
 []
 [:div
  [flow-component]])
