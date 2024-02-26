(ns eth-cljbuild.components.ant-wrappers
  (:require
   ["antd" :as ant]
   [reagent.core :refer [adapt-react-class]]))

(def button (adapt-react-class ant/Button))
(def layout (adapt-react-class ant/Layout))
(def header (adapt-react-class (.-Header ant/Layout)))
(def content (adapt-react-class (.-Content ant/Layout)))
(def flex (adapt-react-class ant/Flex))
(def config-provider (adapt-react-class ant/ConfigProvider))
(def card (adapt-react-class ant/Card))
(def tooltip (adapt-react-class ant/Tooltip))
(def collapse (adapt-react-class ant/Collapse))
(def input (adapt-react-class ant/Input))
(def text-area (adapt-react-class (.-TextArea ant/Input)))
