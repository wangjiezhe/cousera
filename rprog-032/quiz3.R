library(datasets)
data("iris")
str(iris)
mean(iris[iris$Species == 'virginica',]$Sepal.Length)
with(iris, mean(Sepal.Length[Species == 'virginica']))

data("mtcars")
str(mtcars)
sapply(split(mtcars$mpg, mtcars$cyl), mean)
with(mtcars, tapply(mpg, cyl, mean))
tapply(mtcars$mpg, mtcars$cyl, mean)

abs(mean(mtcars[mtcars$cyl == 4,]$hp) - mean(mtcars[mtcars$cyl == 8,]$hp))
with(mtcars, abs(mean(hp[cyl == 4]) - mean(hp[cyl == 8])))
