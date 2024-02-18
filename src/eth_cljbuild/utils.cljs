(ns eth-cljbuild.utils)


(defn find-in
  "Returns the first item in coll for which (pred item) returns true."
  [pred coll]
  (first (filter pred coll)))

(defn ->clj
  "Converts a JavaScript object to a Clojure map"
  [js-obj]
  (js->clj js-obj :keywordize-keys true))

(defn ->js
  "Converts a Clojure map to a JavaScript object"
  [clj-obj]
  (clj->js clj-obj))
