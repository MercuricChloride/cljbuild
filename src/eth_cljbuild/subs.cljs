(ns eth-cljbuild.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::count
 (fn [db]
   (:count db)))

(re-frame/reg-sub
 ::nodes
 (fn [db]
   (:nodes db)))

(re-frame/reg-sub
 ::edges
 (fn [db]
   (:edges db)))
