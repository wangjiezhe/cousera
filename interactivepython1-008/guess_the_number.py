# template for "Guess the number" mini-project
# input will come from buttons and an input field
# all output for the game will be printed in the console
import simplegui
import random

desired_range = 100


# helper function to start and restart the game
def new_game():
    # initialize global variables used in your code here
    global secret_number
    global guess_number
    guess_switcher = {
        100: 7,
        1000: 10
    }
    guess_number = guess_switcher.get(desired_range)
    secret_number = random.randrange(0, desired_range)
    print "New game. Range is [0,%d)" % desired_range
    print "Number of remaining guesses is %d" % guess_number
    print


# define event handlers for control panel
def range100():
    # button that changes the range to [0,100) and starts a new game
    global desired_range
    desired_range = 100
    new_game()


def range1000():
    # button that changes the range to [0,1000) and starts a new game
    global desired_range
    desired_range = 1000
    new_game()


def input_guess(guess):
    global guess_number
    # main game logic goes here
    try:
        guess = int(guess)
        guess_number -= 1
    except ValueError as e:
        return
    print "Guess was %d" % guess
    if secret_number > guess:
        print "Number of remaining guesses is %d" % guess_number
        if guess_number > 0:
            print "Higher"
            print
        else:
            print "You ran out of guesses.  The number was %d" % secret_number
            print
            new_game()
    elif secret_number < guess:
        print "Number of remaining guesses is %d" % guess_number
        if guess_number > 0:
            print "Lower"
            print
        else:
            print "You ran out of guesses.  The number was %d" % secret_number
            print
            new_game()
    else:
        print "Correct"
        print
        new_game()


# create frame
f = simplegui.create_frame("Guess the number", 200, 200)
f.add_button("Range is [0,100)", range100, 200)
f.add_button("Range is [0,1000)", range1000, 200)
f.add_input("Guess", input_guess, 200)

# register event handlers for control elements and start frame
f.start()

# call new_game
new_game()


# always remember to check your completed program against the grading rubric
