"""
Clone of 2048 game.
"""

import poc_2048_gui
import random

# Directions, DO NOT MODIFY
UP = 1
DOWN = 2
LEFT = 3
RIGHT = 4

# Offsets for computing tile indices in each direction.
# DO NOT MODIFY this dictionary.
OFFSETS = {UP: (1, 0),
           DOWN: (-1, 0),
           LEFT: (0, 1),
           RIGHT: (0, -1)}


def merge(line):
    """
    Helper function that merges a single row or column in 2048
    """
    mid_line = [item for item in line if item != 0]
    res_line = []
    iter_i = 0
    while iter_i < len(mid_line):
        item_a = mid_line[iter_i]
        if iter_i == len(mid_line) - 1:
            res_line.append(item_a)
            break
        item_b = mid_line[iter_i + 1]
        if item_a == item_b:
            res_line.append(item_a + item_b)
            iter_i += 2
        else:
            res_line.append(item_a)
            iter_i += 1
    res_line.extend([0] * (len(line) - len(res_line)))
    return res_line


class TwentyFortyEight(object):
    """
    Class to run the game logic.
    """

    def __init__(self, grid_height, grid_width):
        self._grid_height = grid_height
        self._grid_width = grid_width
        self.reset()
        self._initial_squs = {
            UP: [(0, col)
                 for col in range(grid_width)],
            DOWN: [(grid_height - 1, col)
                   for col in range(grid_width)],
            LEFT: [(row, 0)
                   for row in range(grid_height)],
            RIGHT: [(row, grid_width - 1)
                    for row in range(grid_height)]
        }
        self._degrees = {
            UP: grid_height,
            DOWN: grid_height,
            LEFT: grid_width,
            RIGHT: grid_width
        }

    def reset(self):
        """
        Reset the game so the grid is empty except for two
        initial tiles.
        """
        self._grid = [[0 for dummy_col in range(self.get_grid_width())]
                      for dummy_row in range(self.get_grid_height())]
        self.new_tile()
        self.new_tile()

    def __str__(self):
        """
        Return a string representation of the grid for debugging.
        """
        # replace with your code
        return str(self._grid)

    def get_grid_height(self):
        """
        Get the height of the board.
        """
        return self._grid_height

    def get_grid_width(self):
        """
        Get the width of the board.
        """
        return self._grid_width

    def move(self, direction):
        """
        Move all tiles in the given direction and add
        a new tile if any tiles moved.
        """
        changed = False
        off = OFFSETS[direction]
        degs = self.get_degrees(direction)
        initial_squs = self.get_initial_squs(direction)
        squs = [[(squ[0] + off[0] * deg, squ[1] + off[1] * deg)
                 for deg in range(degs)]
                for squ in initial_squs]
        tiles = [[self.get_tile(squ[0] + off[0] * deg, squ[1] + off[1] * deg)
                  for deg in range(degs)]
                 for squ in initial_squs]
        mapping = reduce(lambda dict_a, dict_b: dict(dict_a, **dict_b),
                         [dict(zip(squs[row], merge(tiles[row])))
                          for row in range(len(squs))])
        for key, value in mapping.iteritems():
            if not changed:
                if self.get_tile(key[0], key[1]) != value:
                    self.set_tile(key[0], key[1], value)
                    changed = True
            else:
                self.set_tile(key[0], key[1], value)
        if changed:
            self.new_tile()

    def new_tile(self):
        """
        Create a new tile in a randomly selected empty
        square.  The tile should be 2 90% of the time and
        4 10% of the time.
        """
        tile_r = random.choice([2] * 9 + [4])
        blanks = [(row, col) for col in range(self.get_grid_width())
                  for row in range(self.get_grid_height())
                  if self.get_tile(row, col) == 0]
        if len(blanks) > 0:
            squ_r = random.choice(blanks)
            self.set_tile(squ_r[0], squ_r[1], tile_r)

    def set_tile(self, row, col, value):
        """
        Set the tile at position row, col to have the given value.
        """
        self._grid[row][col] = value

    def get_tile(self, row, col):
        """
        Return the value of the tile at position row, col.
        """
        return self._grid[row][col]

    def get_degrees(self, direction):
        """
        Return the degree of the given direction.
        """
        return self._degrees[direction]

    def get_initial_squs(self, direction):
        """
        Return the initial squares of the given direction.
        """
        return self._initial_squs[direction]


poc_2048_gui.run_gui(TwentyFortyEight(4, 4))
