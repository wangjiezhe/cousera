complete <- function(directory, id = 1:332) {
        ## 'directory' is a character vector of length 1 indicating
        ## the location of the CSV files

        ## 'id' is an integer vector indicating the monitor ID numbers
        ## to be used

        ## Return a data frame of the form:
        ## id nobs
        ## 1  117
        ## 2  1041
        ## ...
        ## where 'id' is the monitor ID number and 'nobs' is the
        ## number of complete cases

        getfullname <- function(i) {
                i <- formatC(i, width = 3, flag = '0')
                filename <- paste(i, 'csv', sep = '.')
                paste(directory, filename, sep = '/')
        }

        completeform <- c()
        for (i in id) {
                fullname <- getfullname(i)
                pollutantdata <- read.csv(fullname)
                completedata <- pollutantdata[
                        !is.na(pollutantdata$sulfate) &
                                !is.na(pollutantdata$nitrate),
                ]
                completecolnum <- nrow(completedata)
                completeform <- rbind(completeform, c(i, completecolnum))
        }

        colnames(completeform) <- c('id', 'nobs')
        data.frame(completeform)
}
