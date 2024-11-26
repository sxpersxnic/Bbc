import pygame
import os

pygame.init()

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
# game window
GAME_SCREEN = pygame.display.set_mode((0, 0), pygame.FULLSCREEN | pygame.DOUBLEBUF)
pygame.display.set_caption("S P A C E I N V A D E R S")
WIDTH, HEIGHT = pygame.display.get_window_size()
TITLE_FONT = pygame.font.SysFont("comicsans", WIDTH // 14)
SUBTITLE_FONT = pygame.font.SysFont("comicsans", WIDTH // 20)
TEXT_FONT = pygame.font.SysFont("comicsans", WIDTH // 40)

##################################################################################

# Menu scene

MENU_BACKGROUND = pygame.transform.scale(pygame.image.load(os.path.join("Images2", "MENU_BACKGROUND.png")),
                                         (WIDTH, HEIGHT))

MENU_TEXT = TITLE_FONT.render("SPACE INVADERS", True, WHITE)
MENU_TEXT_RECT = MENU_TEXT.get_rect()
MENU_TEXT_RECT.center = WIDTH // 2, HEIGHT // 10

MENU_TEXT2 = SUBTITLE_FONT.render("CLICK TO START", True, WHITE)
MENU_TEXT2_RECT = MENU_TEXT2.get_rect()
MENU_TEXT2_RECT.center = WIDTH // 2, HEIGHT // 2

# GameOver scene

GAMEOVER_BACKGROUND = pygame.transform.scale(pygame.image.load(os.path.join("Images2", "GAMEOVER_BACKGROUND.png")),
                                             (WIDTH, HEIGHT))

# Playing scene

PLAYING_BACKGROUND = pygame.transform.scale(pygame.image.load(os.path.join("Images2", "PLAYING_BACKGROUND.png")),
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
# PLAYER IMAGES
SPACESHIP_GREEN = pygame.transform.scale(pygame.image.load(os.path.join("Images2", "SpaceShipGreen.png")),
                                         (WIDTH // 10, HEIGHT // 6))

LASER_GREEN = pygame.transform.scale(pygame.image.load(os.path.join("Images2", "LaserGreen.png")),
                                     (WIDTH // 10, HEIGHT // 20))


##################################################################################
# Ship = Superclass ---> Classes Player and Enemy will use class Ship

class Bullet:
    def __init__(self, bullet_x, bullet_y):
        self.bullet_x = bullet_x
        self.bullet_y = bullet_y
        self.bullet_speed = 3
        self.bullet_x_size = 7
        self.bullet_y_size = 21

    def bullet_move(self):
        self.bullet_y -= self.bullet_speed

    def draw(self):
        pygame.draw.rect(GAME_SCREEN,RED,
                         (self.bullet_x, self.bullet_y, self.bullet_x_size, self.bullet_y_size))


class Player:
    def __init__(self, x, y, player_speed):
        self.x = x
        self.y = y
        self.player_size = WIDTH // 16
        self.player_speed = player_speed
        self.player_lives = 3

        self.bullets = []
        self.bullet_cooldown = 500
        self.last_bullet_time = 0

    def draw_player(self):
        pygame.draw.rect(GAME_SCREEN,WHITE,(self.x,self.y,self.player_size,self.player_size))

    def move_left(self):
        self.x -= self.player_speed
        if self.x < 0:
            self.x = 0

    def move_right(self):
        self.player_x += self.player_speed
        if self.player_x > self.screen_width - self.player_x_size:
            self.player_x = self.screen_width - self.player_y_size

    def move_up(self):
        self.player_y -= self.player_speed
        if self.player_y < 0:
            self.player_y = 0

    def move_down(self):
        self.player_y += self.player_speed
        if self.player_y > self.screen_height - self.player_y_size:
            self.player_y = self.screen_height - self.player_y_size

    def handle_keys(self):
        keys = pygame.key.get_pressed()
        if keys[pygame.K_LEFT]:
            self.move_left()
        if keys[pygame.K_RIGHT]:
            self.move_right()
        if keys[pygame.K_DOWN]:
            self.move_down()
        if keys[pygame.K_UP]:
            self.move_up()
        if keys[pygame.K_SPACE]:
            current_time = pygame.time.get_ticks()
            if current_time - self.last_bullet_time > self.bullet_cooldown:
                self.shoot()
                self.last_bullet_time = current_time

    def shoot(self):
        bullet_x = self.player_x + self.player_x_size // 2 - 2
        bullet_y = self.player_y

        bullet = Bullet(bullet_x, bullet_y)
        self.bullets.append(bullet)

    def update_bullets(self):
        for bullet in self.bullets:
            bullet.laser_move()
            if bullet.y < 0:
                self.bullets.remove(bullet)



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
