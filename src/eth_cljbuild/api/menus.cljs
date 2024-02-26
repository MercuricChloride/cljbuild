(ns eth-cljbuild.api.menus
  (:require
   [re-frame.core :refer [reg-sub]]))

(reg-sub
 ::node-editor-state
 (fn [db _]
   (get-in db [:menus :node-editor])))
