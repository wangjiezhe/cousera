"""
Monte Carlo Tic-Tac-Toe Player
"""

import random
import poc_ttt_gui
import poc_ttt_provided as provided

# Constants for Monte Carlo simulator
# You may change the values of these constants as desired, but
#  do not change their names.
NTRIALS = 100       # Number of trials to run
SCORE_CURRENT = 1.0  # Score for squares played by the current player
SCORE_OTHER = 1.0   # Score for squares played by the other player


# Add your functions here.
def mc_trial(board, player):
    """
    Play a game starting with the given player
    by making random moves, alternating between players.
    Return when the game is over.
    """
    winner = None
    while winner == None:
        empty_square_list = board.get_empty_squares()
        next_move = empty_square_list[random.randrange(len(empty_square_list))]
        board.move(next_move[0], next_move[1], player)
        winner = board.check_win()
        player = provided.switch_player(player)


def mc_update_scores(scores, board, player):
    """
    Score the completed board and update the scores grid.
    """
    winner = board.check_win()
    if winner == provided.DRAW:
        return None
    elif winner == player:
        score_current_add = SCORE_CURRENT
        score_other_add = -SCORE_OTHER
    else:
        score_current_add = -SCORE_CURRENT
        score_other_add = SCORE_OTHER
    board_dim = board.get_dim()
    for row in range(board_dim):
        for col in range(board_dim):
            content = board.square(row, col)
            if content == provided.EMPTY:
                continue
            elif content == player:
                scores[row][col] += score_current_add
            else:
                scores[row][col] += score_other_add


def get_best_move(board, scores):
    """
    Find all of the empty squares with the maximum score
    and randomly return one of them as a (row, column) tuple.
    """
    empty_square_list = board.get_empty_squares()
    if len(empty_square_list) == 0:
        return None
    max_score = max(scores[square[0]][square[1]]
                    for square in empty_square_list)
    best_move_list = [square for square in empty_square_list
                      if scores[square[0]][square[1]] == max_score]
    best_move = random.choice(best_move_list)
    return best_move


def mc_move(board, player, trials):
    """
    Use the Monte Carlo simulation described above to
    return a move for the machine player in the form
    of a (row, column) tuple.
    """
    board_dim = board.get_dim()
    scores = [[0 for dummy_col in range(board_dim)]
              for dummy_row in range(board_dim)]
    for dummy_trial in range(trials):
        test_board = board.clone()
        mc_trial(test_board, player)
        mc_update_scores(scores, test_board, player)
    best_move = get_best_move(board, scores)
    return best_move


# Test game with the console or the GUI.  Uncomment whichever
# you prefer.  Both should be commented out when you submit
# for testing to save time.

# provided.play_game(mc_move, NTRIALS, False)
poc_ttt_gui.run_gui(3, provided.PLAYERX, mc_move, NTRIALS, False)
