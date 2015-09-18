pollutantmean <- function(directory, pollutant, id = 1:332) {
        ## 'directory' is a character vector of length 1 indicating
        ## the location of the CSV files

        ## 'pollutant' is a character vector of length 1 indicating
        ## the name of the pollutant for which we will calculate the
        ## mean; either "sulfate" or "nitrate".

        ## 'id' is an integer vector indicating the monitor ID numbers
        ## to be used

        ## Return the mean of the pollutant across all monitors list
        ## in the 'id' vector (ignoring NA values)
        ## NOTE: Do not round the result!

        getfullname <- function(i) {
                i <- formatC(i, width = 3, flag = '0')
                filename <- paste(i, 'csv', sep = '.')
                paste(directory, filename, sep = '/')
        }

        pollutantdata <- c()
        for (i in id) {
                fullname <- getfullname(i)
                dataname <- paste('pollutantdata', i, sep = '_')
                assign(dataname, read.csv(fullname))
                pollutantdata <- append(pollutantdata, dataname)
        }

        selectpollutantdata <- c()
        for (dataname in pollutantdata) {
                datacontent <- get(dataname)
                selectpollutant <- datacontent[[pollutant]]
                selectpollutantdata <-
                        append(selectpollutantdata,
                               selectpollutant[!is.na(selectpollutant)])
        }

        mean(selectpollutantdata)
}
