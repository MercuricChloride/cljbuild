(ns eth-cljbuild.components.interactive-editor
  (:require
   [eth-cljbuild.components.ant-wrappers :refer [config-provider content flex
                                                 header layout]]
   [eth-cljbuild.components.editor-panel :refer [editor-panel]]
   [eth-cljbuild.components.flow-graph :refer [flow-component]]
   [eth-cljbuild.api.menus :as menus]
   [re-frame.core :refer [subscribe]]))


(defn interactive-editor
 []
 (let [{:keys [node-id showing? properties]} @(subscribe [::menus/node-editor-state])]
       ;; [api contextHolder] (.useNotification ant/notification)]
   [^{"theme" theme} config-provider
    [flex
     {:style {:height "100vh"
              :width "100vw"}}
     [layout
      [header]
      [content
       {:style {:height "100%"
                :width "100%"}}
       [editor-panel showing? node-id properties]
       [flow-component]]]]]))
