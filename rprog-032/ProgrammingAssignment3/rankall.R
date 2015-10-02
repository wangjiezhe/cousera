## Function rankall takes two arguments:
## an outcome name (outcome) and a hospital ranking (num).
##
## The function reads the outcome-of-care-measures.csv file and
## returns a 2-column data frame containing the hospital in each state
## that has the ranking specified in num.
##
## The function should return a value for every state (some may be NA).
## The first column in the data frame is named hospital,
## which contains the hospital name,
## and the second column is named state,
## which contains the 2-character abbreviation for the state name.
##
## Hospitals that do not have data on a particular outcome should be
## excluded from the set of hospitals when deciding the rankings

rankall <- function(outcome, num = "best") {
        ## Read outcome data
        data <- read.csv("outcome-of-care-measures.csv",
                         colClasses = "character")

        ## Check that state and outcome are valid
        outcome_col <- switch(outcome,
                              "heart attack" = 11,
                              "heart failure" = 17,
                              "pneumonia" = 23)
        if (is.null(outcome_col))
                stop("invalid outcome")
        data[, outcome_col] <- as.numeric(data[, outcome_col])

        res <- matrix(ncol = 2)

        ## For each state, find the hospital of the given rank
        states <- sort(unique(data$State))
        for (state in states) {
                data_in_state <- data[data$State == state,]
                data_in_state <- data_in_state[
                        !is.na(data_in_state[, outcome_col]),]

                o <- order(data_in_state[, outcome_col],
                           data_in_state$Hospital.Name)
                ranked <- data_in_state$Hospital.Name[o]

                if (num == "best")
                        res <- rbind(res, c(ranked[1], state))
                else if (num == "worst")
                        res <- rbind(res, c(ranked[length(ranked)], state))
                else if (is.numeric(num))
                        res <- rbind(res, c(ranked[as.integer(num)], state))
                else
                        stop("invalid num")
        }

        ## Return a data frame with the hospital names and the
        ## (abbreviated) state name
        res <- res[-1,]
        colnames(res) <- c("hospital", "state")
        rownames(res) <- res[, 2]
        as.data.frame(res)
}
