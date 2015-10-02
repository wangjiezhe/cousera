## Function best take two arguments:
## the 2-character abbreviated name of a state and an outcome name.
##
## The function reads the outcome-of-care-measures.csv file and
## returns a character vector with the name of the hospital
## that has the best (i.e. lowest) 30-day mortality for the specified outcome
## in that state.
##
## The hospital name is the name provided in the Hospital.Name variable.
## The outcomes can be one of "heart attack", "heart failure", or "pneumonia".
## Hospitals that do not have data on a particular outcome should be excluded
## from the set of hospitals when deciding the rankings.
##
## If there is a tie for the best hospital for a given outcome,
## then the hospital names should be sorted in alphabetical order
## and the first hospital in that set should be chosen

best <- function(state, outcome) {
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

        ## Return hospital name in that state with lowest 30-day death rate
        data_in_state <- data[data$State == state,]
        data_in_state <- data_in_state[
                !is.na(data_in_state[, outcome_col]),]

        lowest <- min(data_in_state[, outcome_col])
        hospitals <- data_in_state[
                data_in_state[, outcome_col] == lowest,]$Hospital.Name

        hospitals <- sort(hospitals)
        hospitals[1]
}
