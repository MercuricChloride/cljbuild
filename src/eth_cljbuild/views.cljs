(ns eth-cljbuild.views
 (:require
  [eth-cljbuild.components.interactive-editor :refer [interactive-editor]]))

(def theme {"algorithm" "dark"})

(defn main-panel
 []
 [interactive-editor])
