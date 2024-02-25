(ns eth-cljbuild.views
 (:require
  [eth-cljbuild.components.interactive-editor :as interactive-editor]))

(def theme {"algorithm" "dark"})

(defn main-panel
 []
 [interactive-editor/interactive-editor])
