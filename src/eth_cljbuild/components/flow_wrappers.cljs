(ns eth-cljbuild.components.flow-wrappers
  (:require
   ["reactflow" :as rf :default react-flow-component]
   [cljs.spec.alpha :as s]
   [eth-cljbuild.utils :refer [->clj]]
   [reagent.core :as r]))

(def handle (r/adapt-react-class rf/Handle))
(def resizer (r/adapt-react-class rf/NodeResizer))
(def position (->clj rf/Position))
(def node-toolbar (r/adapt-react-class rf/NodeToolbar))
(def react-flow (r/adapt-react-class react-flow-component))
(def background (r/adapt-react-class rf/Background))
(def panel (r/adapt-react-class rf/Panel))

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
  
