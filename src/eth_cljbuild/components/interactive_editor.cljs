(ns eth-cljbuild.components.interactive-editor
  (:require
   [eth-cljbuild.components.ant-wrappers :refer [ConfigProvider Content Flex
                                                 Header Layout]]
   [eth-cljbuild.components.editor-panel :refer [editor-panel]]
   [eth-cljbuild.components.flow-graph :refer [flow-component]]
   [eth-cljbuild.subs :as subs]
   [re-frame.core :refer [subscribe]]))


(defn interactive-editor
 []
 (let [{:keys [node-id showing? properties]} @(subscribe [::subs/editor-panel-state])]
       ;; [api contextHolder] (.useNotification ant/notification)]
   [^{"theme" theme} ConfigProvider
    [Flex
     {:style {:height "100vh"
              :width "100vw"}}
     [Layout
      [Header]
      [Content
       {:style {:height "100%"
                :width "100%"}}
       [editor-panel showing? node-id properties]
       [flow-component]]]]]))
