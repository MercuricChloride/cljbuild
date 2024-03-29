(ns eth-cljbuild.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [eth-cljbuild.events :as events]
   [eth-cljbuild.views :as views]
   [eth-cljbuild.config :as config]
   [eth-cljbuild.api.common :as common]))
   


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init []
  (re-frame/dispatch-sync [::common/initialize-db])
  (dev-setup)
  (mount-root))
