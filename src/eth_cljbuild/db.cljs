(ns eth-cljbuild.db)

(defn -node
  ([label]
   {:id label
    :data {:label label
           :input-count 2
           :output-count 1
           :output-map {}
           :cljs "(let [[a b] inputs] (+ a b))"}
    :type "adder"
    :position {:x 0 :y 0}})
  ([label x y]
   {:id label
    :data {:label label
           :input-count 2
           :output-count 1
           :output-map {}
           :cljs "(let [[a b] inputs] (+ a b))"}
    :type "adder"
    :position {:x x :y y}})
  ([label x y type]
   {:id label
    :data {:label label
           :cljs ""
           :input-count 2
           :output-count 1
           :output-map {}}
    :type type
    :position {:x x :y y}}))

(defn number-node
  [id x y]
  {:id id
   :data {:label "number"
          :input-count 0
          :output-count 1
          :output-map {}}
   :type "number"
   :position {:x x :y y}})

(defonce nodes [(number-node "1" 0 0)
                (number-node "2" 0 0)
                (-node "3" 100 100)])
                

(defonce edges [])

(defonce default-db
  {:user-settings {:theme "light"
                   :grid-style "lines"}
   :nodes nodes
   :edges edges
   :rf-instance {} ;; the current react-flow-instance
   :editor-panel {:showing? false
                  :node-id 0
                  :properties []}
   :context-menu {:showing? false
                  :node-id 0
                  :x 0
                  :y 0
                  :properties []}})
