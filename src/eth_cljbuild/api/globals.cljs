(ns eth-cljbuild.api.globals
  (:require
   [re-frame.core :refer [reg-sub]]))

;; All subscriptions are of this form:
(comment (reg-sub
           ::foo-bar
           (fn [db _query-vector]
            123)))

(reg-sub
 ::rf-instance
 (fn [db _]
  (get-in db [:globals :rf-instance])))

(reg-sub
 ::node-types
 (fn [db _query-vector]
   (get-in db [:globals :node-types])))
