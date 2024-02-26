(ns eth-cljbuild.components.clojure-editor
  (:require
   ["@codemirror/state" :refer [EditorState]]
   ["@codemirror/view" :refer [EditorView keymap]]
   ["@nextjournal/clojure-mode" :refer [complete_keymap default_extensions]]
   ["react" :as react]
   [eth-cljbuild.utils :refer [->js]]))

;; (defn- init-editor
;;   [el]
;;   (let [extensions (concat [(.of keymap complete_keymap)] default_extensions)
;;         state (.create EditorState
;;                        {:doc "Hello world!"
;;                         :extensions extensions})
;;         editor (new EditorView {:state state
;;                                 :parent el
;;                                 :extensions extensions})]
;;     (js/console.log "state" state)
;;     (js/console.log "editor" editor)
;;     (js/console.log "el" el)))


;; (defn Editor
;;   []
;;   (let [editor-ref (r/atom nil)]
;;     [:div
;;      {:ref #(reset! editor-ref %)}
;;      (init-editor @editor-ref)]))

(defn- init-editor
  [el]
  (when el ;; Ensure the element exists
    (let [extensions (->js (concat [(.of keymap complete_keymap)] default_extensions))
          state  (.create EditorState #js {"doc" "Hello world!"
                                           "extensions" extensions})
          _ (js/console.log "el" el)
          editor (new EditorView #js {"state" state
                                      "parent" (->js el)
                                      "extensions" extensions})]
      (js/console.log "state" state)
      (js/console.log "editor" editor)
      (js/console.log "el" el))))

;; We have to create a standalone component so the useEffect hook can work wiht reagent
(defn- EditorComp
  []
  (let [editor-ref (react/useRef nil)]
    (react/useEffect
      (fn []
        (when-let [el (.-current editor-ref)]
          (init-editor el)))
      #js [editor-ref]) ;; Dependency array, re-run effect if editor-ref changes
    [:div {:ref editor-ref
           :style {:height "500px"}}]))

(defn Editor
  []
  [:f> EditorComp])
