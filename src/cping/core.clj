(ns cping.core
  (:gen-class)
  (:require [clojure.java.shell :as shell :refer [sh]]))


(defn- ping!
  "Use the shell to ping the given host"
  [host]
  (sh "ping" "-c" "1" host))

(defn -main
  "Main method for the cping app."
  [& args]
  (if (< (count args) 1)
    (binding [*out* *err*]
      (println "usage: seeping ip_address")
      (System/exit 1)))

  (let [host (first args)]
    (println (str "The host is: " host))
    (let [{:keys [out exit] :as result} (ping! host)]
      (println out)
      (println "Exit code:" exit)
      (println "Ping" (if (= exit 0) "succeeded" "failed"))))

  (System/exit 0))
