"""
Merge function for 2048 game.
"""


def merge(line):
    """
    Function that merges a single row or column in 2048.
    """
    mid_line = [item for item in line if item != 0]
    res_line = []
    iter_i = 0
    while iter_i < len(mid_line):
        item_a = mid_line[iter_i]
        if iter_i == len(mid_line)-1:
            res_line.append(item_a)
            break
        item_b = mid_line[iter_i+1]
        if item_a == item_b:
            res_line.append(item_a+item_b)
            iter_i += 2
        else:
            res_line.append(item_a)
            iter_i += 1
    res_line.extend([0]*(len(line)-len(res_line)))
    return res_line
