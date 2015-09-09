# Rock-paper-scissors-lizard-Spock template
import random

# The key idea of this program is to equate the strings
# "rock", "paper", "scissors", "lizard", "Spock" to numbers
# as follows:
#
# 0 - rock
# 1 - Spock
# 2 - paper
# 3 - lizard
# 4 - scissors

# helper functions


def name_to_number(name):
    # delete the following pass statement and fill in your code below
    switcher = {
        "rock": 0,
        "Spock": 1,
        "paper": 2,
        "lizard": 3,
        "scissors": 4
    }
    number = switcher.get(name, None)
    if number is not None:
        return number
    else:
        print "Invalid name!"

    # convert name to number using if/elif/else
    # don't forget to return the result!


def number_to_name(number):
    # delete the following pass statement and fill in your code below
    switcher = {
        0: "rock",
        1: "Spock",
        2: "paper",
        3: "lizard",
        4: "scissors"
    }
    name = switcher.get(number, None)
    if name is not None:
        return name
    else:
        print "Invalid number!"

    # convert number to a name using if/elif/else
    # don't forget to return the result!


def rpsls(player_choice):
    # delete the following pass statement and fill in your code below
    print
    print "Player chooses %s" % player_choice
    player_number = name_to_number(player_choice)
    comp_number = random.randrange(0, 5)
    comp_choice = number_to_name(comp_number)
    print "Computer chooses %s" % comp_choice
    diff = (comp_number - player_number) % 5
    if diff == 0:
        print "Player and computer tie!"
    elif diff == 1 or diff == 2:
        print "Computer wins!"
    else:
        print "Player wins!"

    # print a blank line to separate consecutive games

    # print out the message for the player's choice

    # convert the player's choice to player_number using the function
    # name_to_number()

    # compute random guess for comp_number using random.randrange()

    # convert comp_number to comp_choice using the function number_to_name()

    # print out the message for computer's choice

    # compute difference of comp_number and player_number modulo five

    # use if/elif/else to determine winner, print winner message


# test your code - THESE CALLS MUST BE PRESENT IN YOUR SUBMITTED CODE
rpsls("rock")
rpsls("Spock")
rpsls("paper")
rpsls("lizard")
rpsls("scissors")

# always remember to check your completed program against the grading rubric
