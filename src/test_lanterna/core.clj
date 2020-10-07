(ns test-lanterna.core
  (:refer-clojure :exclude [flush])
  (:import
   com.googlecode.lanterna.TerminalPosition
   com.googlecode.lanterna.terminal.DefaultTerminalFactory
   com.googlecode.lanterna.terminal.Terminal)
  (:gen-class))

(set! *warn-on-reflection* true)

(defn ^Terminal text-terminal []
  (-> (new DefaultTerminalFactory)
      (.setPreferTerminalEmulator false)
      (.setForceTextTerminal true)
      (.createTerminal)))

(defn start [^Terminal terminal]
  (.enterPrivateMode terminal))

(defn flush [^Terminal terminal]
  (.flush terminal))

(defn put-string
  ([^Terminal terminal ^String s]
   (let [position ^TerminalPosition (.getPosition terminal)]
     (put-string terminal s (.getColumn position) (.getRow position))))
  ([^Terminal terminal ^String s ^Integer column ^Integer row]
   (doall
    (reduce (fn [acc c]
              (.setCursorPosition terminal (:column acc) (:row acc))
              (.putCharacter terminal c)
              (update acc :column inc))
            {:column column :row row}
            s))
   nil))

(defn -main [& args]
  (let [terminal (text-terminal)]
    (start terminal)
    (put-string terminal "Hello TUI!" 10 5)
    (flush terminal)
    (Thread/sleep 1000)))
