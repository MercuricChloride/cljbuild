(ns eth-cljbuild.components.flow-wrappers
  (:require
    ["reactflow" :refer [Handle, Position] :default react-flow]
    [reagent.core :as reagent]))

(defn index->char
  [index]
  (char (+ 97 index)))

(def handle (reagent/adapt-react-class Handle))
(def position (js->clj Position :keywordize-keys true))

;; (defn ports
;;   "Adds ports with `id`s to a node"
;;   [port-component & ids]
;;   [:div.port-container
;;    (map port-component ids)])

(defn input
  "Creates an input handle for a node"
  ([id]
   (input :Left id))
  ([pos id]
   (reagent/as-element
    [:div.input-handle [handle {:type "target" :position (pos position) :id id}]])))

(defn output
  "Creates an output handle for a node"
  ([id]
   (output :Right id))
  ([pos id]
   (reagent/as-element
    [:div.output-handle [handle {:type "source" :position (pos position) :id id}]])))
