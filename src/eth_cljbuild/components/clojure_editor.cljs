(ns eth-cljbuild.components.clojure-editor
  (:require
   ["@codemirror/state" :refer [EditorState]]
   ["@codemirror/view" :refer [EditorView keymap drawSelection]]
   ["@nextjournal/clojure-mode" :refer [complete_keymap default_extensions syntax]]
   ["@codemirror/commands" :refer [history historyKeymap]]
   ["@codemirror/language" :refer [foldGutter syntaxHighlighting defaultHighlightStyle]]
   ["react" :as react]
   [eth-cljbuild.utils :refer [->js]]
   [applied-science.js-interop :as j]))

(def theme
  (.theme EditorView
          (j/lit {".cm-content" {:white-space "pre-wrap"
                                 :padding "10px 0"
                                 :flex "1 1 0"}

                  "&.cm-focused" {:outline "0 !important"}
                  ".cm-line" {:padding "0 9px"
                              :line-height "1.6"
                              :font-size "16px"
                              :font-family "var(--code-font)"}
                  ".cm-matchingBracket" {:border-bottom "1px solid var(--teal-color)"
                                         :color "inherit"}
                  ".cm-gutters" {:background "transparent"
                                 :border "none"}
                  ".cm-gutterElement" {:margin-left "5px"}
                  ;; only show cursor when focused
                  ".cm-cursor" {:visibility "hidden"}
                  "&.cm-focused .cm-cursor" {:visibility "visible"}})))

(defonce extensions #js[theme
                        (history)
                        (syntaxHighlighting defaultHighlightStyle)
                        default_extensions
                        (drawSelection)
                        (foldGutter)
                        (.. EditorState -allowMultipleSelections (of true))
                        (.of keymap historyKeymap)])


(defn- init-editor
  [el]
  (when el ;; Ensure the element exists
    (let [;; extensions (->js (concat [(.of keymap complete_keymap)] default_extensions))
          state  (.create EditorState #js {"doc" "Hello world!"
                                           "extensions" extensions})
          editor (new EditorView #js {"state" state
                                      "parent" el
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
      #js []) ;; Dependency array, re-run effect if editor-ref changes
    [:div {:ref editor-ref
           :style {:height "500px"}}]))

(defn Editor
  []
  [:f> EditorComp])
