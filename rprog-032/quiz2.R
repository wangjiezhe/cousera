f <- function(x) {
        g <- function(y) {
                y + z
        }
        z <- 4
        x + g(x)
}

z <- 10
f(3)


h <- function() {
        x <- 10
        function () {
                x
        }
}

i <- h()
x <- 20
i()
