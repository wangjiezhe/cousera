#!/usr/bin/env Rscript

hw1 <- read.csv('data/hw1_data.csv')

head(hw1, 2)
nrow(hw1)
tail(hw1, 2)
hw1[47,'Ozone']
nrow(hw1[is.na(hw1$Ozone),])
mean(hw1[!is.na(hw1$Ozone),'Ozone'])

hw1_1 <- hw1[!is.na(hw1$Ozone),]
hw1_2 <- hw1_1[hw1_1$Ozone > 31,]
hw1_3 <- hw1_2[hw1_2$Temp > 90,]

mean(hw1_3$Solar.R)
mean(hw1[hw1$Month == 6,]$Temp)
max(hw1_1[hw1_1$Month == 5,]$Ozone)

