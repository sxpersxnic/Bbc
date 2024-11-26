import pygame
import os
import sys

########################################################################################################################
# variables
############################################################
# screen variables
############################################################
# colours

WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
RED = (255, 0, 0)
BLUE = (0, 0, 255)
GREEN = (0, 255, 0)

############################################################
# game variables


LEVEL = 1
LIVES = 5


########################################################################################################################
# classes
############################################################
# Player


class Player(pygame.sprite.Sprite):
    def __init__(self, x, y, ship_img):
        super().__init__()
        self.ship_img = ship_img
        self.mask = pygame.mask.from_surface(self.ship_img)
        self.x = x
        self.y = y
        self.speed = WIDTH // 100

    def draw(self):
        GAME_SCREEN.blit(self.ship_img, (self.x, self.y))

    @staticmethod
    def create_bullet():
        return Bullet(pygame.mouse.get_pos()[0], pygame.mouse.get_pos()[1], BULLET_GREEN)

    def get_width(self):
        return self.ship_img.get_width()

    def get_height(self):
        return self.ship_img.get_height()

    def update(self):
        keys = pygame.key.get_pressed()
        if keys[pygame.K_LEFT] and self.x - self.speed > 0:
            self.x -= self.speed
        if keys[pygame.K_RIGHT] and self.x + self.speed + self.get_width() < WIDTH:
            self.x += self.speed
        if keys[pygame.K_UP] and self.y - self.speed > HEIGHT // 4 * 3:
            self.y -= self.speed
        if keys[pygame.K_DOWN] and self.y + self.speed + self.get_height() < HEIGHT:
            self.y += self.speed

        if keys[pygame.K_SPACE]:
            bullet_group.add(player.create_bullet())


############################################################
# Bullet


class Bullet(pygame.sprite.Sprite):
    def __init__(self, x, y, bullet_img):
        super().__init__()
        self.x = x
        self.y = y
        self.bullet_img = bullet_img
        self.mask = pygame.mask.from_surface(self.bullet_img)

    def update(self):
        self.y -= 5

        if self.y <= 0 - HEIGHT // 3:
            self.kill()


########################################################################################################################
# initialize libraries


pygame.init()

clock = pygame.time.Clock()

########################################################################################################################
# window settings
############################################################
# create display


GAME_SCREEN = pygame.display.set_mode((0, 0), pygame.FULLSCREEN, pygame.DOUBLEBUF)
pygame.display.set_caption("S P A C E I N V A D E R S")
WIDTH, HEIGHT = pygame.display.get_window_size()

############################################################
# images
##############################
# object images ---> spaceship, bullet, enemy
####################
# spaceship


SPACESHIP_GREEN = pygame.transform.scale(pygame.image.load(os.path.join("Images2", "SpaceShipGreen.png")),
                                         (WIDTH // 10, HEIGHT // 6))

####################
# bullet


BULLET_GREEN = pygame.transform.scale(pygame.image.load(os.path.join("Images2", "LaserGreen.png")),
                                      (WIDTH // 10, HEIGHT // 20))

####################
# enemy
# >>> (not chosen yet)
##############################
# scene images ---> backgrounds
#####
# menu


MENU_BACKGROUND = pygame.transform.scale(pygame.image.load(os.path.join("Images2", "MENU_BACKGROUND.png")),
                                         (WIDTH, HEIGHT))

####################
# playing


PLAYING_BACKGROUND = pygame.transform.scale(pygame.image.load(os.path.join("Images2", "PLAYING_BACKGROUND.png")),
                                            (WIDTH, HEIGHT))

# function to make the playing background look like it is going down ---> infinite


position = 0


def infinite_bg():
    global position
    GAME_SCREEN.fill(BLACK)

    GAME_SCREEN.blit(PLAYING_BACKGROUND, (0, position))
    GAME_SCREEN.blit(PLAYING_BACKGROUND, (0, position - PLAYING_BACKGROUND.get_height()))

    position += 0.3
    if abs(position) > PLAYING_BACKGROUND.get_height():
        position = 0


####################
# game over


GAMEOVER_BACKGROUND = pygame.transform.scale(pygame.image.load(os.path.join("Images2", "GAMEOVER_BACKGROUND.png")),
                                             (WIDTH, HEIGHT))

############################################################
# font and text settings
##############################
# font


TITLE_FONT = pygame.font.SysFont("comicsans", WIDTH // 14)  # comicsans = font
SUBTITLE_FONT = pygame.font.SysFont("comicsans", WIDTH // 20)  # comicsans = font
TEXT_FONT = pygame.font.SysFont("comicsans", WIDTH // 40)  # comicsans = font

##############################
# menu


MENU_TEXT = TITLE_FONT.render("SPACE INVADERS", True, WHITE)
MENU_TEXT_RECT = MENU_TEXT.get_rect()
MENU_TEXT_RECT.center = WIDTH // 2, HEIGHT // 10

MENU_TEXT2 = SUBTITLE_FONT.render("CLICK TO START", True, WHITE)
MENU_TEXT2_RECT = MENU_TEXT2.get_rect()
MENU_TEXT2_RECT.center = WIDTH // 2, HEIGHT // 2

##############################
# playing


# shows how many lives left until game over
LIVES_LABEL = TEXT_FONT.render(f"LIVES: {LIVES}", True, RED)
LIVES_LABEL_RECT = LIVES_LABEL.get_rect()
LIVES_LABEL_RECT.center = WIDTH - WIDTH // 10, HEIGHT // 8

# shows in which lvl the user is at the moment
LEVEL_LABEL = TEXT_FONT.render(f"LEVEL: {LEVEL}", True, RED)
LEVEL_LABEL_RECT = LEVEL_LABEL.get_rect()
LEVEL_LABEL_RECT.center = WIDTH // 10, HEIGHT // 8

##############################
# game over
# >>> (nothing yet, game over txt is shown by background, but will be changed)


########################################################################################################################
# create objects, sprite groups, etc.
############################################################
# create player and player group


player = Player(WIDTH // 2, HEIGHT - WIDTH // 8, SPACESHIP_GREEN)
player_group = pygame.sprite.Group()
player_group.add(player)


############################################################
# create bullet and bullet group


bullet_group = pygame.sprite.Group()


########################################################################################################################
# game loop variables


MAIN = False
MENU = False
PLAYING = False
GAMEOVER = False


############################################################
# Main loop bullet tutorial
if __name__ == "__main__":
    while not MAIN:
        ############################################################
        # MENU

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

        ############################################################
        # PLAYING

        if not MENU:
            PLAYING = True

        while PLAYING:
            for playing_event in pygame.event.get():
                if playing_event.type == pygame.KEYUP:
                    if playing_event.key == pygame.K_ESCAPE:
                        exit()
                    if playing_event.key == pygame.K_END:
                        PLAYING = False

            infinite_bg()
            GAME_SCREEN.blit(LIVES_LABEL, LIVES_LABEL_RECT)
            GAME_SCREEN.blit(LEVEL_LABEL, LEVEL_LABEL_RECT)
            bullet_group.draw(GAME_SCREEN)
            player_group.draw(GAME_SCREEN)

            bullet_group.update()
            player_group.update()
            pygame.display.flip()
            clock.tick(120)

        ############################################################
        # GAME OVER
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

# USED LINKS FOR TUTORIALS AND OTHERS
############################################################
# SPRITES               --->    https://www.youtube.com/watch?v=MYaxPa_eZS0
# BULLETS               --->    https://www.youtube.com/watch?v=JmpA7TU_0Ms
# INFINITE BACKGROUND   --->    https://www.youtube.com/watch?v=clm7kv88XPs
