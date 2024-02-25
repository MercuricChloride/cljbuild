(ns eth-cljbuild.db)

(defn -node
  ([label]
   {:id label
    :data {:label label
           :input-count 2
           :output-count 1}
    :type "adder"
    :position {:x 0 :y 0}})
  ([label x y]
   {:id label
    :data {:label label
           :input-count 2
           :output-count 1}
    :type "adder"
    :position {:x x :y y}})
  ([label x y type]
   {:id label
    :data {:label label
           :css "body, html {width: 100%; height: 100%; margin: 0; padding: 0}"
           :js ""
           :html "<iframe id=\"responsive-iframe\" height=\"100%\" width=\"100%\" src=\"https://www.youtube.com/embed/HKgSMTON4fI?si=K3omdo0kVaBf7Pqf\">\n</iframe>"
           :cljs-script ""
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
