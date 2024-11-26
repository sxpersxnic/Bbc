from random import randint
import time


def print_log(message, typ=""):
    tag = ""

    if typ == "error":
        tag = "X"
    elif typ == "info":
        tag = "?"
    elif typ == "ok":
        tag = "+"
    else:
        tag = ">"

    print(f"[{tag}]  {message}")

##################################################################################

class Enemy:
    color = "blue"

    def __init__(self, size, start_x, start_y):
        self.size = size
        self.x = start_x
        self.y = start_y

        self.square = self.calc_square()

        #print_log(f"Enemy created! size: {self.size} | x: {self.x} | y: {self.y}", "info")

    def calc_square(self):
        s = self.size ** 2
        return s

    def go_down(self, step=1):
        self.y -= step

    @staticmethod
    def say_color():
        print(Enemy.color)


####################################################################################


enemies = []

for i in range(0, 10):
    e = Enemy(randint(1,5), randint(0, 10), randint(0,10))
    enemies.append(e)


Enemy.say_color()


while True:
    j = 0
    for enemy in enemies:
        enemy.go_down(randint(0,2))
        print(f"[{j}] --> Y-Pos: {enemy.y}")
        j += 1

    time.sleep(1)

