set.seed(1)
rpois(5, 2)

set.seed(10)
x <- rep(0:1, each = 5)
e <- rnorm(10, 0, 20)
y <- 0.5 + 2 * x + e
# plot(x, y)

library(datasets)
x1 <- rnorm(100, 1)
x2 <- rnorm(100, 2)
y <- rnorm(100, 3)
Rprof(tmp <- tempfile())
fit <- lm(y ~ x1 + x2)
Rprof(NULL)
summaryRprof(tmp)
unlink(tmp)
