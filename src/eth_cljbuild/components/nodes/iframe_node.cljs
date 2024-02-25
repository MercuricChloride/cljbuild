(ns eth-cljbuild.components.nodes.iframe-node
  (:require
   [eth-cljbuild.subs :as subs]
   [eth-cljbuild.utils :refer [->js]]
   [eth-cljbuild.components.flow-wrappers :refer [input output]]
   [reagent.core :as r]
   [re-frame.core :refer [subscribe dispatch]]
   [eth-cljbuild.components.nodes.base-node :refer [BaseNode]])
  (:require-macros
   [eth-cljbuild.macros :refer [def-element]]))

(defonce init-js
  "
class PortValues {
  values = {};

  setValue(key, value) {
    this.values[key] = value;
  }

  getValue(key) {
    return this.values[key];
  }
}

window.inputValues = new PortValues(['a']);
window.outputValues = new PortValues(['a']);

window.addEventListener(
  'message',
  (event) => {
    let port = event.data.port;
    let value = event.data.value;
    if (port && value) {
        window.inputValues.setValue(port, value);
    }
    console.log(window.inputValues);
  },
  false
);

console.log('INPUT VALUES', window.inputValues);
")

(defn format-html
  [{:keys [html css js]}]
  (str
   (str "<style>" css "</style>")
   html
   (str "<script>" init-js "</script>")
   (str "<script>" js "</script>")))

(defn send-message
  [iframe]
  (let [win (.-contentWindow iframe)]
    (.postMessage win #js{"port" "a"
                          "value" 42})))

(defn
  IFrameNode
  [{:keys [id data]}]
  (let [{:keys [label input-count output-count]} data
        !iframe (atom nil)
        connected-nodes @(subscribe [::subs/connections id])]
        ;_ (js/console.log "connected-nodes" (->js connected-nodes))]
    [BaseNode
     id
     label
     [:<>
       ;;[Button {:onClick #(send-message @!iframe)} "send-message"]
       [:iframe {:srcDoc (format-html data)
                 :style {:width "100%"
                         :height "100%"}
                 :ref (fn [el] (reset! !iframe el))}]]]))
     ;; (map #(r/as-element [input :Left (str %)]) (range input-count))
     ;; (map #(r/as-element [output :Right (str %)]) (range output-count))]))
