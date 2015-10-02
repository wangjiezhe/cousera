## Function rankhospital takes three arguments:
## the 2-character abbreviated name of a state (state), an outcome (outcome),
## and the ranking of a hospital in that state for that outcome (num).
##
## The function reads the outcome-of-care-measures.csv file and
## returns a character vector with the name of the hospital
## that has the ranking specified by the num argument.
##
## It may occur that multiple hospitals have the same 30-day mortality rate
## for a given cause of death.
## In those cases ties should be broken by using the hospital name.
##
## One can use the `order` function to sort multiple vectors in this manner
## (i.e. where one vector is used to break ties in another vector).

rankhospital <- function(state, outcome, num = "best") {
        ## Read outcome data
        data <- read.csv("outcome-of-care-measures.csv",
                         colClasses = "character")

        ## Check that state and outcome are valid
        states <- unique(data$State)
        if (!state %in% states)
                stop("invalid state")

        outcome_col <- switch(outcome,
                              "heart attack" = 11,
                              "heart failure" = 17,
                              "pneumonia" = 23)
        if (is.null(outcome_col))
                stop("invalid outcome")
        data[, outcome_col] <- as.numeric(data[, outcome_col])

        ## Return hospital name in that state with the given rank
        ## 30-day death rate
        data_in_state <- data[data$State == state,]
        data_in_state <- data_in_state[
                !is.na(data_in_state[, outcome_col]),]

        o <- order(data_in_state[, outcome_col], data_in_state$Hospital.Name)
        res <- data_in_state$Hospital.Name[o]

        if (num == "best")
                return(res[1])
        else if (num == "worst")
                return(res[length(res)])
        else if (is.numeric(num))
                return(res[as.integer(num)])
        else
                stop("invalid num")
}
