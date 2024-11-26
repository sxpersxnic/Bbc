#############################################################
# Import Libraries

from ursina import *

#############################################################
# Initialize Libraries
app = Ursina()

#############################################################
# Define Constant Variables

current_color = color.hex('#008800')

CUBE = Entity(model="cube", color=current_color, texture="tilemap_test_level", scale=2)


#############################################################
# Images
#############################################################
# Create Game Window

#############################################################
# Fonts
#############################################################
# Create Game Scenes
#############################################################
# Sprite Classes
#############################################################
# Logics


def update():
    # ROTATE CONTROLS

    if held_keys['w']:
        CUBE.rotation_x += 1

    if held_keys['s']:
        CUBE.rotation_x -= 1

    if held_keys['a']:
        CUBE.rotation_y += 1

    if held_keys['d']:
        CUBE.rotation_y -= 1

    # MOVE CONTROLS

    if held_keys['right arrow'] and CUBE.x < 7.0:
        CUBE.x += 1
    if held_keys['left arrow'] and CUBE.x > -7.0:
        CUBE.x -= 1
    if held_keys['up arrow'] and CUBE.y < 4.0:
        CUBE.y += 1
    if held_keys['down arrow'] and CUBE.y > -4.0:
        CUBE.y -= 1

    print(f"POSITION: X = {CUBE.x} Y = {CUBE.y} | ROTATION: X = {CUBE.rotation_x} Y = {CUBE.rotation_y}")


#############################################################
# Main Loop

app.run()

#############################################################
# Help Links --->
