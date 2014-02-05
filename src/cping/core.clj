(ns cping.core
  (:gen-class)
  (:require [clojure.java.shell :as shell :refer [sh]]
            [clojure.core.async :refer [chan <!! >!!]]
            [cping.scheduler :as scheduler]))


(defn- ping!
  "Use the shell to ping the given host"
  [host]
  (sh "ping" "-W" "1" "-c" "1" host))


(defn- nstr
  "Repeat the given string N times"
  [n string]
  (apply str (repeat n string)))


(defn summarize
  "Create the summary histogram"
  [good]
  (let [last-10 (max 0 (- good 50))
        first-50 (int (/ (+ (min 0 (- good 50)) 50) 10))]
    (format "|%s%s%s%s| %s"
            (nstr first-50 "#")
            (nstr (- 5 first-50) ".")
            (nstr last-10 "%")
            (nstr (- 10 last-10) ".")
            good)))


(defn- ping-loop
  "Ping the given host every time a tick arrives on the given channel. After 60
  samples, print out a summary."
  [host clock-chan]
  (println (str "The host is: " host))
  (loop [sec 1, good-old 0]
    (<!! clock-chan)
    (let [{:keys [out exit] :as result} (ping! host)
          good? (zero? exit)
          good (if good? (inc good-old) good-old)
          last? (= sec 60)]
      (print (if good? "#" "."))
      (flush)
      (if last?
        (do
          (println (summarize good))
          (recur 1 0))
        (recur (inc sec) good)))))


(defn -main
  "Main method for the cping app."
  [& args]
  (if (< (count args) 1)
    (binding [*out* *err*]
      (println "usage: seeping ip_address")
      (System/exit 1)))

  (let [host (first args)
        clock (chan)]
    (scheduler/periodically (fn [] (>!! clock :tick)) 0 1000)
    (ping-loop host clock))

  (System/exit 0))
