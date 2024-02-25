(ns eth-cljbuild.components.flow-wrappers
  (:require
    ["reactflow" :refer [Handle Position NodeResizer NodeToolbar] :default react-flow]
    [reagent.core :as reagent]
    [cljs.spec.alpha :as s]))

(defn index->char
  [index]
  (char (+ 97 index)))

(def handle (reagent/adapt-react-class Handle))
(def resizer (reagent/adapt-react-class NodeResizer))
(def position (js->clj Position :keywordize-keys true))
(def node-toolbar (reagent/adapt-react-class NodeToolbar))

(s/def ::position
  #(#{:Left :Right :Top :Bottom} %))

(defn input
  "Creates an input handle for a node"
  ([id]
   (input :Left id))
  ([pos id]
   (assert (s/valid? ::position pos) "Invalid position")
   [handle {:type "target"
            :position (position pos)
            :id id
            :className "eth-build-handle"}])
  ([pos id input-component]
   (assert (s/valid? ::position pos) "Invalid position")
   [handle {:type "target"
            :position (position pos)
            :id id
            :className "eth-build-handle"}]))


(defn output
  "Creates an output handle for a node"
  ([id]
   (output :Right id))
  ([pos id]
   (assert (s/valid? ::position pos) "Invalid position")
   [handle {:type "source"
            :position (position pos)
            :id id
            :className "eth-build-handle"}]))
  
