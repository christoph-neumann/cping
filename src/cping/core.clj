(ns cping.core
  (:gen-class))

(use '[clojure.java.shell :only [sh]])

(defn ping
  "Use the shell to ping the given host"
  [host]
  (sh "ping" "-W" "1" "-c" "1" host))

(defn -main
  "Main method for the cping app."
  [& args]
  (if (< (count args) 1)
    ((.println System/err "usage: seeping ip_address")
     (System/exit 1)))

  (def host (nth args 0))
  (println (str "The host is: " host))

  (def result (ping host))
  (println (:out result))
  (println (str "Exit code: " (:exit result)))
  (if (= (:exit result) 0)
    (println "Ping succeeded")
    (println "Ping failed"))

  (System/exit 0))
