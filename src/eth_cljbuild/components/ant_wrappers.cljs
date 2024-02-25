(ns eth-cljbuild.components.ant-wrappers
  (:require
   ["antd" :as ant]
   [reagent.core :refer [adapt-react-class]]))

(def Button (adapt-react-class ant/Button))
(def Layout (adapt-react-class ant/Layout))
(def Header (adapt-react-class (.-Header ant/Layout)))
(def Content (adapt-react-class (.-Content ant/Layout)))
(def Flex (adapt-react-class ant/Flex))
(def ConfigProvider (adapt-react-class ant/ConfigProvider))
(def Card (adapt-react-class ant/Card))
(def Tooltip (adapt-react-class ant/Tooltip))
(def Collapse (adapt-react-class ant/Collapse))
(def Input (adapt-react-class ant/Input))
(def TextArea (adapt-react-class (.-TextArea ant/Input)))
