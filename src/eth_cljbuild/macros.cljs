(ns eth-cljbuild.macros
  (:require
    [re-frame.core :as re-frame]))

(defmacro dispatch
  [& forms]
  `(fn [] (re-frame.core/dispatch [~@forms])))
