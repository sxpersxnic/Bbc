# Youtube tutorial: https://youtu.be/Q-__8Xw9KTM?feature=shared
import pygame
import os
import time
import random

pygame.init()

# variables
level = 1
lives = 5
white = (255, 255, 255)
black = (0, 0, 0)
red = (255, 0, 0)
blue = (0, 0, 255)
green = (0, 255, 0)

# window
screen_width = 800
screen_height = screen_width // 4 * 3
game_screen = pygame.display.set_mode((screen_width, screen_height))
pygame.display.set_caption("SpaceInvaders")

# images ---> Only examples, self-made ones will be in the final version

# Enemy images
RED_ENEMY = pygame.image.load(os.path.join("Images", "RedEnemy.png"))
PURPLE_ENEMY = pygame.image.load(os.path.join("Images", "PurpleEnemy.png"))
BLUE_ENEMY = pygame.image.load(os.path.join("Images", "BlueEnemy.png"))

# Bullet image
YELLOW_LASER = pygame.image.load(os.path.join("Images", "Bullet.png"))
# Player image
SPACESHIP_GREEN = pygame.transform.scale(pygame.image.load(os.path.join("Images", "SpaceShipGreen.png")),
                                         (screen_width // 16, screen_height // 16))

# Background image
BACKGROUND = pygame.transform.scale(pygame.image.load(os.path.join("Images", "SpaceBackGround.png")),
                                    (screen_width, screen_height))


# Player/Ship class
class Ship:
    def __init__(self, x, y, health=100):
        self.x = x
        self.y = y
        self.ship_x_size = screen_width // 16
        self.ship_y_size = screen_width // 16
        self.health = health
        self.speed = screen_width // 50
        self.ship_img = SPACESHIP_GREEN  # filler ---> spaceship image will go in here
        self.laser_img = None  # filler ---> laser image will go in here
        self.lasers = []
        self.cool_down_counter = 0

    def draw(self, window):
        # pygame.draw.rect(window, red, (self.x, self.y, self.ship_x_size, self.ship_y_size))
        window.blit(self.ship_img, (self.x, self.y))

    def get_width(self):
        return self.ship_img.get_width()

    def get_height(self):
        return self.ship_img.get_height()


class Player(Ship):
    def __init__(self, x, y, health=100):
        super().__init__(x, y, health)
        self.ship_img = SPACESHIP_GREEN
        self.laser_img = YELLOW_LASER
        self.mask = pygame.mask.from_surface(self.ship_img)
        self.max_health = health  # Healthbar


class Enemy(Ship):
    COLOR_MAP = {
        "red": ()
    }

    def __init__(self, x, y, health=100):
        super().__init__(x, y, health)


def main():
    run = True
    FPS = 60
    users_lives = 5
    main_font = pygame.font.SysFont("comicsans", screen_width // 32)  # comicsans = font

    player = Player(screen_width // 2, screen_height - screen_width // 8)

    clock = pygame.time.Clock()

    def redraw_window():
        game_screen.blit(BACKGROUND, (0, 0))
        # draw text

        # lives
        lives_label = main_font.render(f"Lives: {users_lives}", True, white)
        lives_label_rect = lives_label.get_rect()
        lives_label_rect.center = (screen_width // 10, screen_height // 10)

        # level
        level_label = main_font.render(f"Level: {level}", True, white)
        level_label_rect = level_label.get_rect()
        level_label_rect.center = (screen_width // 10, screen_height // 6)
        # show texts on screen
        game_screen.blit(lives_label, lives_label_rect)
        game_screen.blit(level_label, level_label_rect)
        # draw player
        player.draw(game_screen)

        pygame.display.update()

    while run:
        clock.tick(FPS)
        redraw_window()

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                run = False

        keys = pygame.key.get_pressed()
        if keys[pygame.K_LEFT] and player.x - player.speed > 0:
            player.x -= player.speed
        if keys[pygame.K_RIGHT] and player.x + player.speed + player.get_width() < screen_width:
            player.x += player.speed
        if keys[pygame.K_UP] and player.y - player.speed > screen_height // 4 * 3:
            player.y -= player.speed
        if keys[pygame.K_DOWN] and player.y + player.speed + player.get_height() < screen_height:
            player.y += player.speed


main()