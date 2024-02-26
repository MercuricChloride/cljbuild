(ns eth-cljbuild.components.context-menu
  (:require
   [shadow.cljs.devtools.server.remote-ext :refer [subscribe]]))


(defn ContextMenu
  [])
  ;; (let [{:keys [showing? x y properties node-id]} @(subscribe [::menus/conte])]
  ;;   (when showing?
  ;;     [:div.context-menu
  ;;      {:style {:top y
  ;;               :left x
  ;;               :z-index 1000}}
  ;;      (str "Node: " node-id)
  ;;      [Collapse
  ;;       {:items (map-indexed (fn [index property] {:key index
  ;;                                                  :label property
  ;;                                                  :children [:input property]}) properties)}]]))
