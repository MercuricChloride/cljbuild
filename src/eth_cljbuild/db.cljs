(ns eth-cljbuild.db)

(defn -node
  ([label]
   {:id label
    :data {:label label}
    :position {:x 0 :y 0}})
  ([label x y]
   {:id label
    :data {:label label}
    :position {:x x :y y}}))

(def nodes [(-node "1")
            (-node "2" 100 0)])

(def edges [{:id "1-2"
             :source "1"
             :target "2"}])

(def default-db
  {:name "re-frame"
   :count 0
   :nodes nodes
   :edges edges})
