import pygame
import os
from pygame import sprite
pygame.init()

WIDTH = 800
HEIGHT = 600


GAME_SCREEN = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("S H O O T M E C H")

SHIP_IMG = pygame.transform.scale(pygame.image.load(os.path.join("Images", "SpaceShipGreen.png")),
                                  (WIDTH // 10, HEIGHT // 6))


class Ship:
    def __init__(self, x, y, ship_img):
        self.x = x
        self.y = y
        self.speed = WIDTH // 300
        self.ship_img = ship_img

    def draw(self):
        GAME_SCREEN.blit(self.ship_img, (self.x, self.y))

    def get_width(self):
        return self.ship_img.get_width()

    def get_height(self):
        return self.ship_img.get_height()


class Bullet(Ship):
    def __init__(self, x, y, ship_img):
        super().__init__(x, y, ship_img)
        self.ship_img = ship_img
        self.mask = pygame.mask.from_surface(self.ship_img)


class Player(Ship):
    def __init__(self, x, y, ship_img):
        super().__init__(x, y, ship_img)
        self.ship_img = ship_img
        self.mask = pygame.mask.from_surface(self.ship_img)

    def movement_handling(self):
        keys = pygame.key.get_pressed()
        if keys[pygame.K_LEFT] and self.x - self.speed > 0:
            self.x -= self.speed
        if keys[pygame.K_RIGHT] and self.x + self.speed + self.get_width() < WIDTH:
            self.x += self.speed
        if keys[pygame.K_UP] and self.y - self.speed > HEIGHT // 4 * 3:
            self.y -= self.speed
        if keys[pygame.K_DOWN] and self.y + self.speed + self.get_height() < HEIGHT:
            self.y += self.speed


player = Player(WIDTH // 2, HEIGHT // 8 * 6, SHIP_IMG)

while True:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            exit()

    GAME_SCREEN.fill((255, 255, 255))
    player.draw()
    pygame.display.update()
    player.movement_handling()
    print(f"Width: {WIDTH} Height: {HEIGHT}")
