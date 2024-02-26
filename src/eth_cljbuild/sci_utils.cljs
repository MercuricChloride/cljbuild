(ns eth-cljbuild.sci-utils
  (:require
   [sci.core :as sci]))

(defn sci-eval
  [input-count inputs code]
  (when (= (count inputs) input-count)
    (println "EVALUATION" (= (count inputs) (int input-count)))
    (let [sci-inputs (sci/new-var 'inputs inputs)
          output (sci/eval-string code {:namespaces {'user {'inputs sci-inputs}}})]
      (js/console.log "OUTPUT: " output)
      output)))
