(ns test-lanterna.core
  (:refer-clojure :exclude [flush])
  (:import
   com.googlecode.lanterna.TerminalPosition
   com.googlecode.lanterna.terminal.ansi.UnixTerminal
   com.googlecode.lanterna.terminal.ansi.CygwinTerminal
   com.googlecode.lanterna.terminal.Terminal)
  (:gen-class))

(set! *warn-on-reflection* true)

(defn windows? []
  (-> (System/getProperty "os.name" "")
      (.toLowerCase)
      (.startsWith "windows")))

(defn text-terminal []
  (if (windows?)
    (new CygwinTerminal System/in System/out (java.nio.charset.Charset/forName "UTF8"))
    (new UnixTerminal System/in System/out (java.nio.charset.Charset/forName "UTF8"))))

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
