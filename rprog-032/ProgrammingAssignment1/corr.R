corr <- function(directory, threshold = 0) {
        ## 'directory' is a character vector of length 1 indicating
        ## the location of the CSV files

        ## 'threshold' is a numeric vector of length 1 indicating the
        ## number of completely observed observations (on all
        ## variables) required to compute the correlation between
        ## nitrate and sulfate; the default is 0

        ## Return a numeric vector of correlations
        ## NOTE: Do not round the result!

        pollutantframe <- complete(directory)
        validframe <- pollutantframe[pollutantframe$nobs > threshold,]

        getfullname <- function(i) {
                i <- formatC(i, width = 3, flag = '0')
                filename <- paste(i, 'csv', sep = '.')
                paste(directory, filename, sep = '/')
        }

        correlations = c()
        for (i in validframe$id) {
                fullname <- getfullname(i)
                pollutantdata <- read.csv(fullname)
                completedata <- pollutantdata[
                        !is.na(pollutantdata$sulfate) &
                                !is.na(pollutantdata$nitrate),
                ]
                correlation <- cor(
                        completedata$sulfate, completedata$nitrate
                )
                correlations <- append(correlations, correlation)
        }

        correlations
}
