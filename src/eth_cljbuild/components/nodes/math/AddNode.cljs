(ns eth-cljbuild.components.nodes.math.AddNode
  (:require
   [eth-cljbuild.components.ant-wrappers :refer [Button Card]]
   [eth-cljbuild.components.flow-wrappers :refer [input node-toolbar output
                                                  resizer]]
   [eth-cljbuild.subs :as subs]
   [eth-cljbuild.utils :refer [->clj ->js]]
   [re-frame.core :refer [dispatch subscribe]])
  (:require-macros
   [eth-cljbuild.macros :refer [def-element]]))

(defn BaseNode
  ([id title inputs outputs]
   (BaseNode id title "" inputs outputs))
  ([id title body inputs outputs]
   (let [{:keys [selected]} @(subscribe [::subs/node-data id])]
    [Card
     {:style {:borderRadius "5px"}}
     [node-toolbar
      [Button {:onClick #(dispatch [:remove-node id])} "delete"]
      [Button {:onClick #(dispatch [:copy-node id])} "copy"]
      [Button {:onClick #(dispatch [:edit-node id])} "edit"]]
     [resizer {:height "500px" :width "500px" :isVisible (->clj selected)}]
     [:div.title title]
     body
     [:div.node-inputs (map-indexed #(with-meta %2 {:key %1}) inputs)]
     [:div.base-node-ouputs (map-indexed #(with-meta %2 {:key %1}) outputs)]])))

(def-element
  AddNode
  {:keys [id data]}
  [BaseNode id (:label data)  [[input :Left "a"]
                               [input :Left "b"]]
                              [[output :Right "a"]]])

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
    (.postMessage win (->js {:port "a"
                             :value 42}))))

(def-element
  IFrameNode
  {:keys [id data]}
  (let [{:keys [label]} data
        !iframe (atom nil)
        connected-nodes @(subscribe [::subs/connections id])]
        ;_ (js/console.log "connected-nodes" (->js connected-nodes))]
    [BaseNode
     id
     label
     [:div
       [Button {:onClick #(send-message @!iframe)} "send-message"]
       [:iframe {:srcDoc (format-html data)
                 :style {:width "100%"
                         :height "100%"}
                 :ref (fn [el] (reset! !iframe el))}]]
     [[input :Left "a"]]
     [[output :Right "a"]]]))
