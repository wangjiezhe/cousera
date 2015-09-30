#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Planner for Yahtzee
Simplifications:  only allow discard and roll, only score against upper level
"""

# Used to increase the timeout, if necessary
try:
    import codeskulptor
except ImportError:
    import SimpleGUICS2Pygame.codeskulptor as codeskulptor

codeskulptor.set_timeout(20)


def gen_all_sequences(outcomes, length):
    """
    Iterative function that enumerates the set of all sequences of
    outcomes of given length.
    """

    answer_set = set([()])
    for dummy_idx in range(length):
        temp_set = set()
        for partial_sequence in answer_set:
            for item in outcomes:
                new_sequence = list(partial_sequence)
                new_sequence.append(item)
                temp_set.add(tuple(new_sequence))
        answer_set = temp_set
    return answer_set


def score(hand):
    """
    Compute the maximal score for a Yahtzee hand according to the
    upper section of the Yahtzee score card.

    hand: full yahtzee hand

    Returns an integer score
    """
    return max(sum(id_x for id_x in hand if id_x == id_i)
               for id_i in hand)


def expected_value(held_dice, num_die_sides, num_free_dice):
    """
    Compute the expected value based on held_dice given that there
    are num_free_dice to be rolled, each with num_die_sides.

    held_dice: dice that you will hold
    num_die_sides: number of sides on each die
    num_free_dice: number of dice to be rolled

    Returns a floating point expected value
    """
#    gens = gen_all_sequences(range(1, num_die_sides+1), num_free_dice)
#    hands = [held_dice + rolled_dice for rolled_dice in gens]
    hands = [held_dice + rolled_dice
             for rolled_dice in gen_all_sequences(range(1, num_die_sides + 1),
                                                  num_free_dice)]
    return sum(score(hand) for hand in hands) / float(len(hands))


def gen_all_holds(hand):
    """
    Generate all possible choices of dice from hand to hold.

    hand: full yahtzee hand

    Returns a set of tuples, where each tuple is dice to hold
    """
    def gen_all_combs(outcomes, length):
        """
        Get all combinations
        """
        answer_set = set([()])
        for dummy_idx in range(length):
            temp_set = set()
            for partial_sequence in answer_set:
                for item in outcomes:
                    if item not in partial_sequence:
                        new_sequence = list(partial_sequence)
                        new_sequence.append(item)
                        new_sequence.sort()
                        temp_set.add(tuple(new_sequence))
            answer_set = temp_set
        return answer_set

#    indices = [gen_all_combs(range(len(hand)), i)
#               for i in range(0, len(hand)+1)]
#    res = [set([tuple(hand[k] for k in j) for j in tuple(i)])
#           for i in indices]
#    res = reduce(lambda a, b: a.union(b), res)
#    return res
    return reduce(lambda set_a, set_b: set_a.union(set_b),
                  [set([tuple(hand[id_k] for id_k in id_j)
                        for id_j in tuple(id_i)])
                   for id_i in [gen_all_combs(range(len(hand)), id_i)
                                for id_i in range(0, len(hand) + 1)]])


def strategy(hand, num_die_sides):
    """
    Compute the hold that maximizes the expected value when the
    discarded dice are rolled.

    hand: full yahtzee hand
    num_die_sides: number of sides on each die

    Returns a tuple where the first element is the expected score and
    the second element is a tuple of the dice to hold
    """
    held_dices = gen_all_holds(hand)
    res = (0.0, ())
    for held_dice in held_dices:
        ex_v = expected_value(held_dice, num_die_sides,
                              len(hand) - len(held_dice))
        if ex_v > res[0]:
            res = (ex_v, held_dice)
    return res


def run_example():
    """
    Compute the dice to hold and expected score for an example hand
    """
    num_die_sides = 6
    hand = (1, 1, 1, 5, 6)
    hand_score, hold = strategy(hand, num_die_sides)
    print "Best strategy for hand", hand, "is to hold", hold, \
        "with expected score", hand_score


run_example()


# import poc_holds_testsuite
# poc_holds_testsuite.run_suite(gen_all_holds)
