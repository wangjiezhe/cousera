# template for "Stopwatch: The Game"
try:
    import simplegui
except ImportError:
    import SimpleGUICS2Pygame.simpleguics2pygame as simplegui

# define global variables
total_time = 0
total_stop = 0
win_stop = 0
running = False


# define helper function format that converts time
# in tenths of seconds into formatted string A:BC.D
def format(t):
    secs = t // 10
    tenths_of_secs = t % 10
    mins = secs // 60
    secs = secs % 60
    return "%d:%02d.%d" % (mins, secs, tenths_of_secs)


# define event handlers for buttons; "Start", "Stop", "Reset"
def start():
    timer.start()
    global running
    running = True


def stop():
    timer.stop()
    global total_stop, win_stop, running
    if running:
        running = False
        total_stop += 1
        if total_time % 10 == 0:
            win_stop += 1


def reset():
    timer.stop()
    global total_time, total_stop, win_stop, running
    running = False
    total_time = 0
    total_stop = 0
    win_stop = 0


# define event handler for timer with 0.1 sec interval
def update():
    global total_time
    total_time += 1


# define draw handler
def draw(canvas):
    global total_time
    if total_time < 6000:
        canvas.draw_text(format(total_time),
                         (80, 130), 50, "White")
    else:
        timer.stop()
        total_time = 0
        canvas.draw_text('Timeout!',
                         (80, 130), 50, "White")
    canvas.draw_text("%d/%d" % (win_stop, total_stop),
                     (250, 30), 30, "Red")


# create frame
frame = simplegui.create_frame('Stopwatch', 300, 200)
timer = simplegui.create_timer(100, update)

# register event handlers
frame.add_button('start', start, 100)
frame.add_button('stop', stop, 100)
frame.add_button('reset', reset, 100)
frame.set_draw_handler(draw)

# start frame
frame.start()

# Please remember to review the grading rubric
