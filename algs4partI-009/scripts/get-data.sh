#!/bin/sh

DATA_DIR="../data/"
DATA_URL="http://algs4.cs.princeton.edu/code/algs4-data.zip"
DATA_FILE="algs4-data.zip"

mkdir -p ${DATA_DIR}
cd ${DATA_DIR}
curl -O ${DATA_URL}
unzip ${DATA_FILE} && rm ${DATA_FILE}
