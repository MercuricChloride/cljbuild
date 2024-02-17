(ns eth-cljbuild.db
  (:require [eth-cljbuild.components.nodes.math.AddNode :refer [AddNode IFrameNode]]))

(defn -node
  ([label]
   {:id label
    :data {:label label}
    :type "adder"
    :position {:x 0 :y 0}})
  ([label x y]
   {:id label
    :data {:label label}
    :type "adder"
    :position {:x x :y y}})
  ([label x y type]
   {:id label
    :data {:label label
           :html "
  <style>
    body, html {width: 100%; height: 100%; margin: 0; padding: 0}
  </style>
    <iframe id=\"responsive-iframe\" height=\"100%\" width=\"100%\" src=\"https://www.youtube.com/embed/HKgSMTON4fI?si=K3omdo0kVaBf7Pqf\"></iframe>
  <script src=\"/sample.js\" />
"}
    :type type
    :position {:x x :y y}}))

(defonce nodes [(-node "1")
                (-node "2" 100 100)
                (-node "3" 200 200 :iframe)])

(defonce edges []) ;; [{:id "1-2"
                   ;;   :source "1"
                   ;;   :target "2"}]


(defonce node-types
  (clj->js {:adder AddNode
            :iframe IFrameNode}))

(defonce default-db
  {:nodes nodes
   :edges edges
   :node-types node-types
   :context-menu {:showing? false
                  :node-id 0
                  :x 0
                  :y 0
                  :properties []}})
