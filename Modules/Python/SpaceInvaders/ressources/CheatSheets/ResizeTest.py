import pygame
pygame.init()

# Colours
white = (255, 255, 255)
black = (0, 0, 0)
purple = (255, 0, 255)
red2 = (100, 0, 0)
red = (255, 0, 0)
blue = (0, 0, 200)
yellow = (255, 255, 0)

# Menu screen
screen_width, screen_height = 800, 600
game_screen = pygame.display.set_mode((screen_width, screen_height), flags=pygame.RESIZABLE | pygame.DOUBLEBUF)
pygame.display.set_caption("Space Invaders")


# Player & Bullet
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
        pygame.draw.rect(game_screen, purple,
                         (self.bullet_x, self.bullet_y, self.bullet_x_size, self.bullet_y_size))


class Player:
    def __init__(self, player_x_size, player_y_size, player_speed):
        self.screen_width = screen_width
        self.screen_height = screen_height
        self.player_x_size = player_x_size
        self.player_y_size = player_y_size
        self.player_speed = player_speed
        self.player_lives = 3

        self.player_x = (self.screen_width - self.player_x_size) // 2
        self.player_y = self.screen_height - screen_height // 6

        self.bullets = []
        self.bullet_cooldown = 500
        self.last_bullet_time = 0

    def draw_player(self):
        pygame.draw.rect(game_screen, white, (self.player_x, self.player_y, self.player_x_size, self.player_y_size))

    def move_left(self):
        self.player_x -= self.player_speed
        if self.player_x < 0:
            self.player_x = 0

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


# Player
player = Player(screen_width // 16, screen_width // 16, screen_width // 50)
shot = Bullet(player.player_x, player.player_y)


# Menu Text
menu_font = pygame.font.Font(None, screen_width // 8)
menu_text = menu_font.render("Space Invaders", True, black)
menu_text_rect = menu_text.get_rect()
menu_text_rect.center = (screen_width // 2, screen_height // 6)

# Start game Text
start_font = pygame.font.Font(None, screen_width // 16)
start_text = start_font.render("Click here to start", True, black)
start_text_rect = start_text.get_rect()
start_text_rect.center = (screen_width // 2, screen_height // 2)

# Game Text
game_font = pygame.font.Font(None, screen_width // 16)
game_text = game_font.render(f"lives: {player.player_lives}", True, white)
game_text_rect = game_text.get_rect()
game_text_rect.center = (screen_width // 8, screen_height // 8)

# End Text
end_font = pygame.font.Font(None, screen_width // 8)
end_text = end_font.render("GAME OVER", True, black)
end_text_rect = end_text.get_rect()
end_text_rect.center = (screen_width // 2, screen_height // 2)


# Definition of game state
game_started = False
game_ended = False
menu_started = False


# Menu loop
def menu():
    global game_started, menu_started, game_screen, screen_width, screen_height
    menu_started = True
    while menu_started:
        for event_menu in pygame.event.get():
            if event_menu.type == pygame.QUIT:
                pygame.quit()

            if event_menu.type == pygame.VIDEORESIZE:
                screen_width, screen_height = game_screen.get_size()
                game_screen = pygame.display.set_mode((screen_width, screen_height),
                                                      flags=pygame.RESIZABLE | pygame.DOUBLEBUF)
                menu_text_rect.center = (screen_width // 2, screen_height // 6)
                start_text_rect.center = (screen_width // 2, screen_height // 2)
                game_text_rect.center = (screen_width // 8, screen_height // 8)
                player.screen_width = screen_width
                player.player_x = (screen_width - player.player_x_size) // 2
                player.player_y = screen_height - screen_height // 6
                end_text_rect.center = (screen_width // 2, screen_height // 2)

            elif event_menu.type == pygame.MOUSEBUTTONDOWN and not game_started:
                if start_text_rect.collidepoint(event_menu.pos):
                    game_started = True
                    menu_started = False
                    print(screen_width, screen_height)

        game_screen.fill(white)

        if game_started:
            break
        else:
            game_screen.blit(menu_text, menu_text_rect)
            game_screen.blit(start_text, start_text_rect)

        pygame.display.flip()

# Game loop
def game():
    global game_started, game_ended, shot, screen_width, screen_height, game_screen
    while game_started:
        for event_game in pygame.event.get():
            if event_game.type == pygame.KEYDOWN:
                if event_game.key == pygame.K_END:
                    game_ended = True
            if event_game.type == pygame.QUIT:
                pygame.quit()

            if event_game.type == pygame.VIDEORESIZE:
                screen_width, screen_height = game_screen.get_size()
                game_screen = pygame.display.set_mode((screen_width, screen_height),
                                                      flags=pygame.RESIZABLE | pygame.DOUBLEBUF)
                menu_text_rect.center = (screen_width // 2, screen_height // 6)
                start_text_rect.center = (screen_width // 2, screen_height // 2)
                game_text_rect.center = (screen_width // 8, screen_height // 8)
                player.screen_width = screen_width
                player.player_x = (screen_width - player.player_x_size) // 2
                player.player_y = screen_height - screen_height // 6
                end_text_rect.center = (screen_width // 2, screen_height // 2)
                print(screen_width, screen_height)

        game_screen.fill(black)
        if game_ended:
            game_started = False
        else:
            player.handle_keys()

            game_screen.blit(game_text, game_text_rect)

            player.draw_player()
            player.update_bullets()

            for shot in player.bullets:
                shot.draw()

        pygame.display.flip()


# End loop
def end():
    global game_started, game_ended, menu_started, game_screen, screen_width, screen_height
    while game_ended:
        for event_end in pygame.event.get():
            if event_end.type == pygame.KEYDOWN:
                if event_end.key == pygame.K_SPACE:
                    menu_started = True
            if event_end.type == pygame.QUIT:
                pygame.quit()

            if event_end.type == pygame.VIDEORESIZE:
                screen_width, screen_height = game_screen.get_size()
                game_screen = pygame.display.set_mode((screen_width, screen_height),
                                                      flags=pygame.RESIZABLE | pygame.DOUBLEBUF)
                menu_text_rect.center = (screen_width // 2, screen_height // 6)
                start_text_rect.center = (screen_width // 2, screen_height // 2)
                game_text_rect.center = (screen_width // 8, screen_height // 8)
                player.screen_width = screen_width
                player.player_x = (screen_width - player.player_x_size) // 2
                player.player_y = screen_height - screen_height // 6
                end_text_rect.center = (screen_width // 2, screen_height // 2)
                print(screen_width, screen_height)

        game_screen.fill(white)
        if menu_started:
            game_ended = False
        else:
            game_screen.blit(end_text, end_text_rect)

        pygame.display.flip()


# Game loop
game_loop = True
while game_loop:
    for event in pygame.event.get():
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_ESCAPE:
                game_loop = False  # Exit the game loop

    if not game_ended:
        menu_started = True
        menu()
    if not menu_started:
        game_started = True
        game()
    if not game_started:
        game_ended = True
        end()
    pygame.display.flip()

pygame.quit()
