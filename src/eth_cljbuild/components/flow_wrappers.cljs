(ns eth-cljbuild.components.flow-wrappers
  (:require
    ["reactflow" :refer [Handle Position NodeResizer] :default react-flow]
    [reagent.core :as reagent]
    [cljs.spec.alpha :as s]))

(defn index->char
  [index]
  (char (+ 97 index)))

(def handle (reagent/adapt-react-class Handle))
(def resizer (reagent/adapt-react-class NodeResizer))
(def position (js->clj Position :keywordize-keys true))

(s/def ::position
  #(#{:Left :Right :Top :Bottom} %))

(defn input
  "Creates an input handle for a node"
  ([id]
   (input :Left id))
  ([pos id]
   (assert (s/valid? ::position pos) "Invalid position")
   (reagent/as-element
    [:div.input-handle
     [handle {:type "target" :position (position pos) :id id}]])))

(defn output
  "Creates an output handle for a node"
  ([id]
   (output :Right id))
  ([pos id]
   (reagent/as-element
    [:div.output-handle [handle {:type "source" :position (position pos) :id id}]]))) ;; {:type "source" :position (pos position) :id id}
