(defproject cping "0.1.0-SNAPSHOT"
  :description "Simple project for visualizing ping results over time. Let's you \"see\" ping. Get it?"
  :url "https://github.com/christoph-neumann/cping"
  :license {:name "Apache Public License v2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :main ^:skip-aot cping.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
