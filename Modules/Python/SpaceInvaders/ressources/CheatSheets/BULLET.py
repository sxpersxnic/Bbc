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
    def __init__(self):
        super().__init__()
        self.image = SPACESHIP_GREEN

    def update(self):
        self.image.center = pygame.mouse.get_pos()

    @staticmethod
    def create_bullet():
        return Bullet(pygame.mouse.get_pos()[0], pygame.mouse.get_pos()[1])


############################################################
# Bullet


class Bullet(pygame.sprite.Sprite):
    def __init__(self, pos_x, pos_y):
        super().__init__()
        self.image = BULLET_GREEN
        self.rect = self.image.get_rect(center=(pos_x, pos_y))

    def update(self):
        self.rect.y -= 5

        if self.rect.y <= 0 - HEIGHT // 3:
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


def background_moving():  # link ro used tutorial (not directly copied, just the logic) --->
    global position  # https://www.youtube.com/watch?v=clm7kv88XPs
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


# mouse is invisible (used in tutorial for bullet management, link ---> https://www.youtube.com/watch?v=JmpA7TU_0Ms

pygame.mouse.set_visible(False)

########################################################################################################################
# create objects, sprite groups, etc.
############################################################
# create player and player group


player = Player()
player_group = pygame.sprite.Group()
player_group.add(player)

############################################################
# create bullet and bullet group

bullet_group = pygame.sprite.Group()

########################################################################################################################
# Main loop ---> if __name__ == "__main__":
############################################################
# Main loop bullet tutorial

while True:
    ############################################################
    # handle events im the game loop

    for event in pygame.event.get():

        if event.type == pygame.QUIT:
            exit()

        if event.type == pygame.MOUSEBUTTONDOWN:
            bullet_group.add(player.create_bullet())

    ############################################################
    # drawing

    GAME_SCREEN.fill((255, 255, 255))
    bullet_group.draw(GAME_SCREEN)
    GAME_SCREEN.blit(player.image, (WIDTH // 2, HEIGHT // 2))
    bullet_group.update()
    player_group.update()
    pygame.display.flip()
    clock.tick(120)
