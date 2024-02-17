(ns eth-cljbuild.macros)

(defmacro dispatch
  [& forms]
  `(fn [] (re-frame.core/dispatch [~@forms])))

(defmacro def-element
  "Defines a new Reagent component with the given name and body"
  {:clj-kondo/ignore [:unresolved-symbol :type-mismatch]}
  [name props & body]
  `(cljs.core/defn ~name
     [~'p]
     (let [~props (~'cljs.core/js->clj ~'p ~':keywordize-keys true)]
       (~'reagent.core/as-element
         ~@body))))
