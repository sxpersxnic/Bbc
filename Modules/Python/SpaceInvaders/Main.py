import pygame
import os

##################################################################################
# variables

WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
RED = (255, 0, 0)
BLUE = (0, 0, 255)
GREEN = (0, 255, 0)
LEVEL = 1
LIVES = 5


##################################################################################
# Initialize Pygame

pygame.init()


##################################################################################
# game window and metadata initialisation

GAME_SCREEN = pygame.display.set_mode((0, 0), pygame.FULLSCREEN | pygame.DOUBLEBUF)
pygame.display.set_caption("S P A C E I N V A D E R S")
WIDTH, HEIGHT = pygame.display.get_window_size()
TITLE_FONT = pygame.font.SysFont("comicsans", WIDTH // 14)  # comicsans = font
SUBTITLE_FONT = pygame.font.SysFont("comicsans", WIDTH // 20)  # comicsans = font
TEXT_FONT = pygame.font.SysFont("comicsans", WIDTH // 40)  # comicsans = font
# game metadata initialisation


##################################################################################

# Object Images

SPACESHIP_GREEN = pygame.transform.scale(pygame.image.load(os.path.join("Images", "SpaceShipGreen.png")),
                                         (WIDTH // 10, HEIGHT // 6))

LASER_GREEN = pygame.transform.scale(pygame.image.load(os.path.join("Images", "LaserGreen.png")),
                                     (WIDTH // 10, HEIGHT // 20))


##################################################################################

# Menu scene


MENU_BACKGROUND = pygame.transform.scale(pygame.image.load(os.path.join("Images", "MENU_BACKGROUND.png")),
                                         (WIDTH, HEIGHT))

MENU_TEXT = TITLE_FONT.render("SPACE INVADERS", True, WHITE)
MENU_TEXT_RECT = MENU_TEXT.get_rect()
MENU_TEXT_RECT.center = WIDTH // 2, HEIGHT // 10

MENU_TEXT2 = SUBTITLE_FONT.render("CLICK TO START", True, WHITE)
MENU_TEXT2_RECT = MENU_TEXT2.get_rect()
MENU_TEXT2_RECT.center = WIDTH // 2, HEIGHT // 2

# GameOver scene

GAMEOVER_BACKGROUND = pygame.transform.scale(pygame.image.load(os.path.join("Images", "GAMEOVER_BACKGROUND.png")),
                                             (WIDTH, HEIGHT))

# Playing scene

PLAYING_BACKGROUND = pygame.transform.scale(pygame.image.load(os.path.join("Images", "PLAYING_BACKGROUND.png")),
                                            (WIDTH, HEIGHT))

position = 0


def background_moving():  # https://www.youtube.com/watch?v=clm7kv88XPs
    global position
    GAME_SCREEN.fill(BLACK)

    GAME_SCREEN.blit(PLAYING_BACKGROUND, (0, position))
    GAME_SCREEN.blit(PLAYING_BACKGROUND, (0, position - PLAYING_BACKGROUND.get_height()))

    position += 0.3
    if abs(position) > PLAYING_BACKGROUND.get_height():
        position = 0


LIVES_LABEL = TEXT_FONT.render(f"LIVES: {LIVES}", True, RED)
LIVES_LABEL_RECT = LIVES_LABEL.get_rect()
LIVES_LABEL_RECT.center = WIDTH - WIDTH // 10, HEIGHT // 8
LEVEL_LABEL = TEXT_FONT.render(f"LEVEL: {LEVEL}", True, RED)
LEVEL_LABEL_RECT = LEVEL_LABEL.get_rect()
LEVEL_LABEL_RECT.center = WIDTH // 10, HEIGHT // 8


##################################################################################
# classes and functions
# Ship = Superclass ---> Classes Player and Enemy will use class Ship
class Ship:
    def __init__(self, x, y, ship_img, laser_img):
        self.x = x
        self.y = y
        self.speed = WIDTH // 100
        self.ship_img = ship_img
        self.laser_img = laser_img
        self.lasers = []
        self.laser_x = x // 2
        self.laser_y = y // 2

    def draw(self):
        GAME_SCREEN.blit(self.ship_img, (self.x, self.y))

    def get_width(self):
        return self.ship_img.get_width()

    def get_height(self):
        return self.ship_img.get_height()


class Player(Ship):
    def __init__(self, x, y, ship_img, laser_img):
        super().__init__(x, y, ship_img, laser_img)
        self.ship_img = ship_img
        self.mask = pygame.mask.from_surface(self.ship_img)
        self.laser_img = laser_img
        self.mask_bullet = pygame.mask.from_surface(self.laser_img)

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


##################################################################################
# main loop's variables
PLAYER = Player(WIDTH // 2, HEIGHT - WIDTH // 8, SPACESHIP_GREEN, LASER_GREEN)
MAIN = False
MENU = False
PLAYING = False
GAMEOVER = False
# main loop
if __name__ == "__main__":
    while not MAIN:
        for main_event in pygame.event.get():
            if main_event.type == pygame.KEYUP:
                if main_event.key == pygame.K_ESCAPE:
                    exit()
        MENU = True
        while MENU:
            for menu_event in pygame.event.get():
                if menu_event.type == pygame.KEYUP:
                    if menu_event.key == pygame.K_ESCAPE:
                        exit()

                if menu_event.type == pygame.MOUSEBUTTONDOWN:
                    if MENU_TEXT2_RECT.collidepoint(menu_event.pos):
                        MENU = False

            GAME_SCREEN.blit(MENU_BACKGROUND, (0, 0))
            GAME_SCREEN.blit(MENU_TEXT, MENU_TEXT_RECT)
            GAME_SCREEN.blit(MENU_TEXT2, MENU_TEXT2_RECT)
            pygame.display.update()

        if not MENU:
            PLAYING = True

        while PLAYING:
            for playing_event in pygame.event.get():
                if playing_event.type == pygame.KEYUP:
                    if playing_event.key == pygame.K_ESCAPE:
                        exit()
                    if playing_event.key == pygame.K_END:
                        PLAYING = False
            background_moving()
            GAME_SCREEN.blit(LIVES_LABEL, LIVES_LABEL_RECT)
            GAME_SCREEN.blit(LEVEL_LABEL, LEVEL_LABEL_RECT)
            PLAYER.draw()
            pygame.display.update()
            PLAYER.movement_handling()

        if not PLAYING:
            GAMEOVER = True

        while GAMEOVER:
            for gameover_event in pygame.event.get():
                if gameover_event.type == pygame.KEYUP:
                    if gameover_event.key == pygame.K_ESCAPE:
                        exit()

                    if gameover_event.key == pygame.K_SPACE:
                        GAMEOVER = False

            GAME_SCREEN.blit(GAMEOVER_BACKGROUND, (0, 0))
            pygame.display.update()
        if not GAMEOVER:
            MENU = True

pygame.quit()
