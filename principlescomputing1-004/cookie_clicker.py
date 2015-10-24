#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Cookie Clicker Simulator
"""

from __future__ import print_function
import poc_clicker_provided as provided
import math

try:
    import simpleplot
except ImportError:
    import SimpleGUICS2Pygame.simpleplot as simpleplot

# Used to increase the timeout, if necessary
try:
    import codeskulptor
except ImportError:
    import SimpleGUICS2Pygame.codeskulptor as codeskulptor
codeskulptor.set_timeout(20)


# Constants
SIM_TIME = 10000000000.0
# SIM_TIME = 100.0


class ClickerState(object):
    """
    Simple class to keep track of the game state.
    """

    def __init__(self):
        self._total_cookies = 0.0
        self._cur_cookies = 0.0
        self._cur_time = 0.0
        self._cur_cps = 1.0
        self._hist = [(0.0, None, 0.0, 0.0)]

    def __str__(self):
        """
        Return human readable state
        """
        return """
Time: %.1f
Current cookies: %.1f
CPS: %.1f
Total cookies: %.1f
History length: %d\n""" % (self.get_time(), self.get_cookies(),
                           self.get_cps(), self._total_cookies,
                           len(self.get_history()))

    def get_cookies(self):
        """
        Return current number of cookies
        (not total number of cookies)

        Should return a float
        """
        return self._cur_cookies

    def get_cps(self):
        """
        Get current CPS

        Should return a float
        """
        return self._cur_cps

    def get_time(self):
        """
        Get current time

        Should return a float
        """
        return self._cur_time

    def get_history(self):
        """
        Return history list

        History list should be a list of tuples of the form:
        (time, item, cost of item, total cookies)

        For example: [(0.0, None, 0.0, 0.0)]

        Should return a copy of any internal data structures,
        so that they will not be modified outside of the class.
        """
        return self._hist[:]

    def time_until(self, cookies):
        """
        Return time until you have the given number of cookies
        (could be 0.0 if you already have enough cookies)

        Should return a float with no fractional part
        """
        cur_cookies = self.get_cookies()
        cps = self.get_cps()
        if cookies <= cur_cookies:
            return 0.0
        else:
            return math.ceil((cookies - cur_cookies) / cps)

    def wait(self, time):
        """
        Wait for given amount of time and update state

        Should do nothing if time <= 0.0
        """
        if time > 0.0:
            self._cur_time += time
            add_cookies = self._cur_cps * time
            self._cur_cookies += add_cookies
            self._total_cookies += add_cookies

    def buy_item(self, item_name, cost, additional_cps):
        """
        Buy an item and update state

        Should do nothing if you cannot afford the item
        """
        if self.get_cookies() >= cost:
            self._cur_cookies -= cost
            self._cur_cps += additional_cps
            self._hist.append((self.get_time(), item_name,
                               cost, self._total_cookies))


def simulate_clicker(build_info, duration, strategy):
    """
    Function to run a Cookie Clicker game for the given
    duration with the given strategy.  Returns a ClickerState
    object corresponding to the final state of the game.
    """
    info = build_info.clone()
    state = ClickerState()
    while True:
        cur_time = state.get_time()
        time_left = duration - cur_time
        cookies = state.get_cookies()
        hist = state.get_history()
        cps = state.get_cps()
        if cur_time > duration:
            break
        next_item = strategy(cookies, cps, hist, time_left, info)
        if next_item is None:
            state.wait(time_left)
            break
        elapsed_time = state.time_until(info.get_cost(next_item))
        if elapsed_time > time_left:
            state.wait(time_left)
            break
        state.wait(elapsed_time)
        state.buy_item(next_item, info.get_cost(next_item),
                       info.get_cps(next_item))
        info.update_item(next_item)
    return state


def strategy_cursor_broken(cookies, cps, history, time_left, build_info):
    """
    Always pick Cursor!

    Note that this simplistic (and broken) strategy does not properly
    check whether it can actually buy a Cursor in the time left.  Your
    simulate_clicker function must be able to deal with such broken
    strategies.  Further, your strategy functions must correctly check
    if you can buy the item in the time left and return None if you
    can't.
    """
    return "Cursor"


def strategy_none(cookies, cps, history, time_left, build_info):
    """
    Always return None

    This is a pointless strategy that will never buy anything, but
    that you can use to help debug your simulate_clicker function.
    """
    return None


def strategy_cheap(cookies, cps, history, time_left, build_info):
    """
    Always buy the cheapest item you can afford in the time left.
    """
    info = build_info.clone()
    avail_cookies = cookies + cps * time_left
    linfo = list(zip(info.build_items(),
                     map(info.get_cost, info.build_items())))
    linfo.sort(key=lambda x: x[1])
    if linfo[0][1] <= avail_cookies:
        return linfo[0][0]
    else:
        return None


def strategy_expensive(cookies, cps, history, time_left, build_info):
    """
    Always buy the most expensive item you can afford in the time left.
    """
    info = build_info.clone()
    avail_cookies = cookies + cps * time_left
    linfo = list(zip(info.build_items(),
                     map(info.get_cost, info.build_items())))
    linfo.sort(key=lambda x: x[1], reverse=True)
    for item, cost in linfo:
        if cost <= avail_cookies:
            return item
        else:
            continue
    return None


def strategy_best(cookies, cps, history, time_left, build_info):
    """
    The best strategy that you are able to implement.
    """
    info = build_info.clone()
    avail_cookies = cookies + cps * time_left
    cost_per_cps = [info.get_cost(x) / info.get_cps(x)
                    for x in info.build_items()]
    linfo = list(zip(info.build_items(), cost_per_cps,
                     map(info.get_cost, info.build_items())))
    linfo.sort(key=lambda x: x[1])
    for item, _, cost in linfo:
        if cost <= avail_cookies:
            return item
        else:
            continue
    return None


def run_strategy(strategy_name, time, strategy):
    """
    Run a simulation for the given time with one strategy.
    """
    state = simulate_clicker(provided.BuildInfo(), time, strategy)
    print(strategy_name, ":", state)

    # Plot total cookies over time

    # Uncomment out the lines below to see a plot of total cookies vs. time
    # Be sure to allow popups, if you do want to see it

    history = state.get_history()
    history = [(item[0], item[3]) for item in history]
    simpleplot.plot_lines(strategy_name, 1000, 400, 'Time',
                          'Total Cookies', [history], True)


def run():
    """
    Run the simulator.
    """
    run_strategy("Cursor", SIM_TIME, strategy_cursor_broken)

    # Add calls to run_strategy to run additional strategies
    run_strategy("Cheap", SIM_TIME, strategy_cheap)
    run_strategy("Expensive", SIM_TIME, strategy_expensive)
    run_strategy("Best", SIM_TIME, strategy_best)

run()
