import pygame
pygame.init()

white = (255, 255, 255)
black = (0, 0, 0)
purple = (255, 0, 255)
red2 = (100, 0, 0)
red = (255, 0, 0)
blue = (0, 0, 200)
yellow = (255, 255, 0)


class Bullet:

    def __init__(self, bullet_x, bullet_y):
        self.bullet_x = bullet_x
        self.bullet_y = bullet_y
        self.bullet_speed = 10
        self.bullet_x_size = 7
        self.bullet_y_size = 21

    def bullet_move(self):
        self.bullet_y -= self.bullet_speed

    def draw(self):
        pygame.draw.rect(game_screen, purple,
                         (self.bullet_x, self.bullet_y, self.bullet_x_size, self.bullet_y_size))


class Player:
    def __init__(self, screen_width, screen_height, player_size, player_speed):
        self.screen_width = screen_width
        self.screen_height = screen_height
        self.player_size = player_size
        self.player_speed = player_speed

        self.player_x = (self.screen_width - self.player_size) // 2
        self.player_y = (self.screen_height - self.player_size) // 2

        self.bullets = []

    def draw_player(self):
        pygame.draw.rect(game_screen, white, self.player_size, self.player_size)

    def move_left(self):
        self.player_x -= self.player_speed
        if self.player_x < 0:
            self.player_x = 0

    def move_right(self):
        self.player_x += self.player_speed
        if self.player_x > self.screen_width - self.player_size:
            self.player_x = self.screen_width - self.player_size

    def move_up(self):
        self.player_y -= self.player_speed
        if self.player_y < 0:
            self.player_y = 0

    def move_down(self):
        self.player_y += self.player_speed
        if self.player_y > self.screen_height - self.player_size:
            self.player_y = self.screen_height - self.player_size

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
            self.shoot()

    def shoot(self):
        bullet_x = self.player_x + self.player_size // 2 - 2
        bullet_y = self.player_y

        bullet = Bullet(bullet_x, bullet_y)
        self.bullets.append(bullet)

    def update_bullets(self):
        for bullet in self.bullets:
            bullet.laser_move()
            if bullet.y < 0:
                self.bullets.remove(bullet)

# class Enemy:
# def __init__(self, enemy_, enemy_, enemy_, enemy_,):
# self.
