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

(defn json-stringify
  "Converts a Clojure type to a JSON String"
  [clj-obj]
  (->> clj-obj
       (->js)
       (.stringify js/JSON)))

(defn json-parse
  "Parses a JSON String into a clojure type"
  [json-str]
  (->> json-str
    (.parse js/JSON)
    (->clj)))

(defn local-storage-get
  "Retrieves the value of a key from local storage as a clojure value"
  [key]
  (->> key
       (.getItem js/localStorage)
       (json-parse)))

(defn local-storage-set
  "Stores a clojure value in local storage under a given key"
  [key value]
  (->> value
       (json-stringify)))
       
