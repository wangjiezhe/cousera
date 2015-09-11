#!/bin/sh

JAR_DIR="../lib/"
JAR_URL="http://algs4.cs.princeton.edu/code/algs4.jar"
JAR_FILE="algs4.jar"

mkdir -p ${JAR_DIR}
cd ${JAR_DIR}
curl -O ${JAR_URL}
