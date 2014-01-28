#!/usr/bin/env bash

# Switch to the directory containing the script.
cd $(dirname $(readlink "$0" || echo "$0"))

# Run the app, bulding it if necessary.
JAR="target/cping-0.1.0-SNAPSHOT-standalone.jar"
if [ ! -f "$JAR" ]; then
	lein uberjar
fi
java -server -jar "$JAR" $*
