(ns cping.core
  (:gen-class)
  (:require [clojure.java.shell :as shell :refer [sh]]))


(defn- ping!
  "Use the shell to ping the given host"
  [host]
  (sh "ping" "-W" "1" "-c" "1" host))


(defn- nstr
  "Repeat the given string N times"
  [n string]
  (apply str (repeat n string)))


(defn summarize
  "Create the summary histogram of"
  [good]
  (let [last_10 (max 0 (- good 50))
        first_50 (int (/ (+ (min 0 (- good 50)) 50) 10))]
    (format "|%s%s%s%s| %s"
            (nstr first_50 "#")
            (nstr (- 5 first_50) ".")
            (nstr last_10 "%")
            (nstr (- 10 last_10) ".")
            good)))


(defn -main
  "Main method for the cping app."
  [& args]
  (if (< (count args) 1)
    (binding [*out* *err*]
      (println "usage: seeping ip_address")
      (System/exit 1)))

  (let [host (first args)]
    (println (str "The host is: " host))
    (let [sec (atom 0)
          good (atom 0)]
      (while true
        (let [{:keys [out exit] :as result} (ping! host)]
          (swap! sec inc)
          (if (= exit 0) (swap! good inc))
          (print (if (= exit 0) "#" "."))
          (flush)
          (if (= @sec 60)
            (do
              (println (summarize @good))
              (reset! sec 0)
              (reset! good 0)))
          (Thread/sleep 500)))))

  (System/exit 0))
