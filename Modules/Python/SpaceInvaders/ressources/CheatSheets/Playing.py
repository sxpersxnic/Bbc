 import sys

import pygame

pygame.init()

reference_screen_width = 800
reference_screen_height = 600

screen_width, screen_height = reference_screen_width, reference_screen_height
game_screen = pygame.display.set_mode((screen_width, screen_height), flags=pygame.RESIZABLE | pygame.DOUBLEBUF)
pygame.display.set_caption("SpaceInvaders Gamescreen")

# Colours
white = (255, 255, 255)
black = (0, 0, 0)
purple = (255, 0, 255)
red2 = (100, 0, 0)
red = (255, 0, 0)
blue = (0, 0, 200)
yellow = (255, 255, 0)

# Player settings
player_x_size = 50
player_y_size = 50
player_x = screen_width // 2 - player_x_size // 2
player_x_fraction = 0.5
player_y_fraction = 0.5
player_y = screen_height - 70
player_lives = 3
player_speed = 3 * (screen_width / reference_screen_width)

# Bullet settings
bullet_x_size = 7
bullet_y_size = 21
bullet_speed = 10
bullets = []


def draw_bullet():
    pygame.draw.rect(game_screen, purple, (player_x, player_y, bullet_x_size, bullet_y_size))


def fire_bullet(x, y):
    for bullet in bullets:
        bullet -= bullet_speed
        draw_bullet()
    bullets.append([x + player_x + player_x_size // 2 - 2, y])


def draw_player(x, y):
    pygame.draw.rect(game_screen, red, (x, y, player_x_size, player_y_size))


def move_player():
    global player_x, player_y
    keys = pygame.key.get_pressed()
    if keys[pygame.K_ESCAPE]:
        pygame.quit()
        sys.exit()
    if keys[pygame.K_DOWN] and player_y < screen_height - player_y_size:
        player_y += player_speed
    if keys[pygame.K_UP] and player_y > screen_height // 4 * 3:
        player_y -= player_speed
    if keys[pygame.K_RIGHT] and player_x < screen_width - player_x_size:
        player_x += player_speed
    if keys[pygame.K_LEFT] and player_x > 0:
        player_x -= player_speed
    if keys[pygame.K_SPACE]:
        fire_bullet(player_x, player_y)


# Gamescreen loop

playing = True
while playing:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            playing = False

        elif event.type == pygame.VIDEORESIZE:
            screen_width, screen_height = event.size
            player_speed = int(player_speed * (screen_width / reference_screen_width))
            player_x = screen_width // 2 - player_x_size // 2
            player_y = screen_height - 70

    game_screen.fill(white)

    draw_player(player_x, player_y)
    pygame.display.flip()

    move_player()
    print("player_x:", player_x, "-", "player_y:", player_y, "player_speed:", player_speed)

pygame.quit()
sys.exit()
