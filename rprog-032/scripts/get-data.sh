#!/bin/sh

DATA_DIR="../data"

QUIZ1_DATA_URL="https://d396qusza40orc.cloudfront.net/rprog/data/quiz1_data.zip"
QUIZ1_DATA="quiz1_data.zip"

ASSIGNMENT1_DATA_URL="https://d396qusza40orc.cloudfront.net/rprog/data/specdata.zip"
ASSIGNMENT1_DATA="specdata.zip"

mkdir -p ${DATA_DIR}
cd ${DATA_DIR}

curl ${QUIZ1_DATA_URL} -o ${QUIZ1_DATA}
unzip ${QUIZ1_DATA}
rm ${QUIZ1_DATA}

curl ${ASSIGNMENT1_DATA_URL} -o ${ASSIGNMENT1_DATA}
unzip ${ASSIGNMENT1_DATA}
rm ${ASSIGNMENT1_DATA}
