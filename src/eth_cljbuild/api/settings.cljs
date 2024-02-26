(ns eth-cljbuild.api.settings
  (:require
   [re-frame.core :refer [reg-sub]]))


(reg-sub
 ::theme
 (fn [db _]
   (get-in db [:settings :visual])))
