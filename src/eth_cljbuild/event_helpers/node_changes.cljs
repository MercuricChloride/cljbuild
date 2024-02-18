(ns eth-cljbuild.event-helpers.node-changes
  (:require
   [eth-cljbuild.utils :refer [->js]]))

(defn remove-node
  [id]
  (->js {:id id
         :type "remove"}))

(defn create-node
  [node-data]
  (->js {:item (->js node-data)
         :type "add"}))
