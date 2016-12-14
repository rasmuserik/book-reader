(ns solsort.book-reader.book-reader
  (:require-macros
   [cljs.core.async.macros :refer [go go-loop alt!]]
   [reagent.ratom :as ratom :refer  [reaction]])
  (:require
   [cljs.reader]
   [solsort.toolbox.setup]
   [solsort.toolbox.appdb :refer [db db! db-async!]]
   [solsort.toolbox.ui :refer [input select]]
   [solsort.toolbox.net :refer [<ajax]]
   [solsort.util
    :refer
    [<seq<! js-seq load-style! put!close!
     parse-json-or-nil log page-ready render dom->clj]]
   [reagent.core :as reagent :refer []]
   [clojure.string :as string :refer [replace split blank?]]
   [cljs.core.async :refer [>! <! chan put! take! timeout close! pipe]]))


(when (not (db [:text]))
  (go
    (db! [:text]
         (<! (<ajax "https://mubackend.solsort.com/cors/?http://ia600302.us.archive.org/35/items/treasureisland27780gut/pg27780.txt"
                              :credentials false :result "text")))))
(defn text-view []
  (log (into [:div {:style {:display :inline-block
                            :font-size 16
                            :margin "2ex"
                            :width "65ex"}}]
         (map (fn [s] [:p s])
                    (clojure.string/split (db [:text]) #"\n[\r\t ]*\n")
                    ))))
(defn main []
  [text-view])
(render main)
